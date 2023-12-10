package denis.beck.login_ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import denis.beck.login.databinding.FragmentLoginBinding
import denis.beck.login_ui.di.DaggerLoginComponent
import denis.beck.login_ui.di.LoginDependenciesProvider
import denis.beck.navigation.Navigator
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModel: LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dependencies = (requireActivity() as LoginDependenciesProvider).loginDependencies()
        val component = DaggerLoginComponent.factory().create(requireContext(), viewModelStore, dependencies)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logInButton.setOnClickListener {
            viewModel.onLogIn()
        }
        viewModel.goToReminderList.observe(viewLifecycleOwner) {
            navigator.navigateToMain(parentFragmentManager)
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}