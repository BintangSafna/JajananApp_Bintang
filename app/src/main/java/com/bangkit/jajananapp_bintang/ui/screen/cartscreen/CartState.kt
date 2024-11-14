package com.bangkit.jajananapp_bintang.ui.screen.cartscreen

import com.bangkit.jajananapp_bintang.data.model.OrderJajanan

data class CartState(
    val orderMakanan: List<OrderJajanan>,
    val totalHarga: Int
)