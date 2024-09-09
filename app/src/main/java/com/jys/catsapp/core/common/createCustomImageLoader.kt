package com.jys.catsapp.core.common

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.jys.catsapp.R

fun createCustomImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .logger(DebugLogger())
        .respectCacheHeaders( false )
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.5)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.5)
                .build()
        }
        .placeholder(R.drawable.placeholder)
        .build()
}

