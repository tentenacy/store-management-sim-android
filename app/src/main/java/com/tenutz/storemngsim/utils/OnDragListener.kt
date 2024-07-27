package com.tenutz.storemngsim.utils

interface OnDragListener<VH> {
    fun onDragStart(holder: VH)
    fun onDragOver()
}