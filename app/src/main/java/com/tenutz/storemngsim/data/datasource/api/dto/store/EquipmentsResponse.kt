package com.tenutz.storemngsim.data.datasource.api.dto.store

data class EquipmentsResponse(
    val equipments: List<Equipment>,
) {
    data class Equipment(
        val storeCode: String?,
        val equipmentCode: String?,
        val equipmentType: String?,
        val equipmentName: String?,
        val equipmentIp: String?,
        val partType: Float?,
        val teamViewerNumber: String?,
        val anyDeskNumber: String?,
        val sshPort: String?,
        val gateway: String?,
        val netmask: String?,
        val dns1: String?,
        val dns2: String?,
    )
}
