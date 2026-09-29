package com.sixteenhalves.orangeadmin.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import java.io.File

suspend fun compressImage(
    context: Context,
    file: File,
): File {
    val compressedFile =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Compressor.compress(context, file) {
                format(Bitmap.CompressFormat.WEBP_LOSSY)
                quality(80)
                size(2_097_152)
            }
        } else {
            Compressor.compress(context, file) {
                @Suppress("DEPRECATION")
                format(Bitmap.CompressFormat.WEBP)
                quality(80)
                size(2_097_152)
            }
        }
    return compressedFile
}
