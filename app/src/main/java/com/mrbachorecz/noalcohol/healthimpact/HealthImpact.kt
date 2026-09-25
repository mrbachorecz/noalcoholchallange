package com.mrbachorecz.noalcohol.healthimpact

/**
 * Milestone health effects of abstinence.
 * Based on commonly cited clinical timelines (e.g. Conversation / Medical News Today summaries)
 * and short-term abstinence studies (e.g. BMJ Open 2018: BP, insulin resistance, liver enzymes,
 * cancer-related growth factors after ~1 month).
 *
 * Wording is intentionally cautious: benefits vary by prior drinking level and individual health.
 */
val HEALTH_IMPACTS: Map<Int, HealthImpactInfo> = mapOf(
    1 to HealthImpactInfo(
        title = "24 Hours",
        impacts = listOf(
            "Alcohol typically cleared from blood.",
            "Blood sugar begins to normalize.",
            "Body starts to rehydrate."
        )
    ),
    3 to HealthImpactInfo(
        title = "72 Hours",
        impacts = listOf(
            "Noticeably better hydration.",
            "Improved mental clarity and focus.",
            "Liver shifts toward repair."
        )
    ),
    7 to HealthImpactInfo(
        title = "1 Week",
        impacts = listOf(
            "Sleep quality often improves.",
            "More morning energy for many.",
            "Brain function starts recovering."
        )
    ),
    14 to HealthImpactInfo(
        title = "2 Weeks",
        impacts = listOf(
            "Early fatty-liver repair begins.",
            "Clearer, less puffy skin.",
            "Immune function recovers further."
        )
    ),
    30 to HealthImpactInfo(
        title = "1 Month",
        impacts = listOf(
            "Insulin resistance often drops ~25%.",
            "Blood pressure often falls ~6%.",
            "Liver enzymes usually improve."
        )
    ),
    91 to HealthImpactInfo(
        title = "3 Months",
        impacts = listOf(
            "Liver health keeps improving.",
            "Better memory, focus, and mood.",
            "More stable energy and sleep."
        )
    ),
    182 to HealthImpactInfo(
        title = "6 Months",
        impacts = listOf(
            "Liver damage often largely reversed.",
            "Lower long-term heart disease risk.",
            "Ongoing brain recovery."
        )
    ),
    365 to HealthImpactInfo(
        title = "1 Year",
        impacts = listOf(
            "Lower risk of heart disease and stroke.",
            "Reduced risk of several cancers.",
            "Substantial cognitive recovery."
        )
    ),
)

data class HealthImpactInfo(
    val title: String,
    val impacts: List<String> = emptyList()
)

fun healthImpactForDays(numberOfDays: Int): HealthImpactInfo? {
    if (numberOfDays <= 0) return null
    return HEALTH_IMPACTS
        .toList()
        .sortedBy { (days, _) -> days }
        .lastOrNull { numberOfDays >= it.first }
        ?.second
}
