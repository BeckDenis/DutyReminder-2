package denis.beck.dutyreminder_2.epoxy.models

data class RemindEpoxyDataModel(
    val id: Long,
    val message: String,
    val timestamp: Long,
    val extendedTimeStamp: ExtendedTimeStamp,
    val timeText: String,
) : EpoxyDataModel {
    var onClickListener: () -> Unit = {}
}
