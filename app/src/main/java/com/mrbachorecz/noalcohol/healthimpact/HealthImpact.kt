package com.mrbachorecz.noalcohol.healthimpact

val HEALTH_IMPACTS: Map<Int, HealthImpactInfo> = mapOf(
    1 to HealthImpactInfo(
        title = "24 Hours",
        impacts = listOf(
            "Improved sleep quality.",
            "Blood sugar levels begin to normalize.",
            "Reduced inflammation."
        )
    ),
    3 to HealthImpactInfo(
        title = "72 Hours",
        impacts = listOf(
            "Noticeably better hydration.",
            "Increased mental clarity and focus.",
            "Body starts to repair damage caused by alcohol."
        )
    ),
    7 to HealthImpactInfo(
        title = "1 Week",
        impacts = listOf(
            "Deeper, more restorative sleep.",
            "Skin may start to look clearer and more hydrated.",
            "Reduced anxiety for some individuals."
        )
    ),
    14 to HealthImpactInfo(
        title = "2 Weeks",
        impacts = listOf(
            "Potential for weight loss due to reduced calorie intake.",
            "Liver function begins to improve significantly.",
            "Enhanced immune system function."
        )
    ),
    30 to HealthImpactInfo(
        title = "1 Month",
        impacts = listOf(
            "Noticeable improvement in overall energy levels.",
            "Better mood and reduced mood swings.",
            "Lowered blood pressure for some."
        )
    ),
    91 to HealthImpactInfo(
        title = "3 Months",
        impacts = listOf(
            "Significant reduction in risk for certain types of cancer.",
            "Improved liver health and function.",
            "Better cognitive function and memory."
        )
    ),
    182 to HealthImpactInfo(
        title = "6 Months",
        impacts = listOf(
            "Sustained improvements in mental and physical well-being.",
            "Reduced risk of cardiovascular diseases.",
            "Healthier relationship with your body and mind."
        )
    ),
)

data class HealthImpactInfo(
    val title: String,
    val impacts: List<String> = emptyList()
)
