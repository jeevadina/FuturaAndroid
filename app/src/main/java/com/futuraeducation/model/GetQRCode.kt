package com.futuraeducation.model

data class GetQRCode(
    val `data`: QRData
)

data class QRData(
    val id: String,
    val qrCodeUrl: String,
    val videoUrl: String
)
