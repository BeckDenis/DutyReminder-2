package denis.beck.dutyreminder_2.models

data class RemindPresentationModel(
    val id: Long = 0,
    val time: String,
    val message: String,
) {
    var onClickListener: () -> Unit = {}
}