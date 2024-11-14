package com.bangkit.jajananapp_bintang.data

import com.bangkit.jajananapp_bintang.data.model.OrderJajanan
import com.bangkit.jajananapp_bintang.data.model.jajananData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class JajananRepository {

    private val orderMakanan = mutableListOf<OrderJajanan>()

    init {
        if (orderMakanan.isEmpty()) {
            jajananData.jajanan.forEach {
                orderMakanan.add(OrderJajanan(it, 0))
            }
        }
    }

    fun getsMakanan(): Flow<List<OrderJajanan>> {
        return flowOf(orderMakanan)
    }

    fun getOrderMakananById(makananId: Int): OrderJajanan {
        return orderMakanan.first {
            it.makanan.id == makananId
        }
    }

    fun searchMakanan(query:String): Flow<List<OrderJajanan>> {
        return flowOf(orderMakanan.filter {
            it.makanan.nama.contains(query, ignoreCase = true)
        })
    }

    fun updateOrderMakanan(makananId: Int, newCountValue: Int): Flow<Boolean> {
        val index = orderMakanan.indexOfFirst { it.makanan.id == makananId }
        val result = if (index >= 0) {
            val orderReward = orderMakanan[index]
            orderMakanan[index] =
                orderReward.copy(makanan = orderReward.makanan, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderMakanan(): Flow<List<OrderJajanan>> {
        return getsMakanan()
            .map { orderMakanans ->
                orderMakanans.filter { orderMakanan ->
                    orderMakanan.count != 0
                }
            }
    }

}