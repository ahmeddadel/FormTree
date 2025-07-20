package com.lumiform.data.local.mapper

import com.lumiform.data.remote.dto.ContentItemDto
import com.lumiform.domain.model.ContentItemModel

/**
 * @created 17/07/2025 - 12:08 AM
 * @project FormTree
 * @author adell
 */

/**
 * Maps a [ContentItemDto] to a [ContentItemModel].
 *
 * @return the corresponding [ContentItemModel] or null if the type is not recognized.
 */
fun ContentItemDto.toDomain(): ContentItemModel? = when (type) {
    "page" -> title?.let { title ->
        ContentItemModel.Page(
            title,
            items.orEmpty().mapNotNull { it.toDomain() })
    }

    "section" -> title?.let { title ->
        ContentItemModel.Section(
            title,
            items.orEmpty().mapNotNull { it.toDomain() })
    }

    "text" -> title?.let { title ->
        ContentItemModel.TextQuestion(
            title
        )
    }

    "image" -> if (!src.isNullOrEmpty() && !title.isNullOrEmpty())
        ContentItemModel.ImageQuestion(src, title)
    else
        null

    else -> null
}

/**
 * Maps a [ContentItemModel] to a [ContentItemDto].
 *
 * @return the corresponding [ContentItemDto].
 */
fun ContentItemModel.toDto(): ContentItemDto = when (this@toDto) {
    is ContentItemModel.Page -> ContentItemDto(
        type = "page",
        title = title,
        items = items.map { it.toDto() }
    )

    is ContentItemModel.Section -> ContentItemDto(
        type = "section",
        title = title,
        items = items.map { it.toDto() }
    )

    is ContentItemModel.TextQuestion -> ContentItemDto(
        type = "text",
        title = title
    )

    is ContentItemModel.ImageQuestion -> ContentItemDto(
        type = "image",
        src = src,
        title = title
    )
}
