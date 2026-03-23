package com.skul9x.battu.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * MarkdownText - Simple Markdown renderer for AI results
 * 
 * Supports:
 * - Headers (# ## ###)
 * - Bold (**text**)
 * - Italic (*text*)
 * - Bullet lists (- item)
 * - Line breaks
 * 
 * Note: This is a simplified Markdown parser for basic formatting.
 * For full Markdown support, consider using a library like Markwon or Compose-Markdown.
 */
@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val lines = markdown.split("\n")
        
        for (line in lines) {
            when {
                // Header 1
                line.startsWith("# ") -> {
                    Text(
                        text = line.removePrefix("# "),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Header 2
                line.startsWith("## ") -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = line.removePrefix("## "),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Header 3
                line.startsWith("### ") -> {
                    Text(
                        text = line.removePrefix("### "),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                
                // Bullet list
                line.trimStart().startsWith("- ") || line.trimStart().startsWith("• ") -> {
                    Row(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = "• ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = parseInlineMarkdown(line.trimStart().removePrefix("- ").removePrefix("• ")),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                // Empty line
                line.isBlank() -> {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                
                // Regular paragraph
                else -> {
                    Text(
                        text = parseInlineMarkdown(line),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                }
            }
        }
    }
}

/**
 * Parse inline Markdown (bold, italic)
 */
@Composable
private fun parseInlineMarkdown(text: String): androidx.compose.ui.text.AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val boldRegex = """\*\*(.+?)\*\*""".toRegex()
        val italicRegex = """\*(.+?)\*""".toRegex()
        
        // Find all bold and italic matches
        val boldMatches = boldRegex.findAll(text).toList()
        val italicMatches = italicRegex.findAll(text).filter { italicMatch ->
            // Exclude italic matches that are inside bold matches
            boldMatches.none { boldMatch ->
                italicMatch.range.first >= boldMatch.range.first && 
                italicMatch.range.last <= boldMatch.range.last
            }
        }.toList()
        
        val allMatches = (boldMatches + italicMatches).sortedBy { it.range.first }
        
        for (match in allMatches) {
            // Append text before match
            if (currentIndex < match.range.first) {
                append(text.substring(currentIndex, match.range.first))
            }
            
            // Append styled text
            when {
                match.value.startsWith("**") -> {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(match.groupValues[1])
                    }
                }
                match.value.startsWith("*") -> {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(match.groupValues[1])
                    }
                }
            }
            
            currentIndex = match.range.last + 1
        }
        
        // Append remaining text
        if (currentIndex < text.length) {
            append(text.substring(currentIndex))
        }
    }
}
