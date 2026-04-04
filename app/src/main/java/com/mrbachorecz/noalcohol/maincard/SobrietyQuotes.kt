val SOBRIETY_QUOTES: Map<Int, List<String>> = mapOf(
    // 0 - 1 day: The Beginning
    0 to listOf(
        "The journey of a thousand miles begins with a single step.",
        "Your future self will thank you for starting today.",
        "Today is the first day of the rest of your life."
    ),
    // 1 - 7 days: The First Week
    1 to listOf(
        "One day at a time. You've already proven you can start.",
        "Focus on making it through today. Tomorrow will take care of itself.",
        "The first few days are the hardest. Stay strong."
    ),
    // 7 - 30 days: Building Momentum
    7 to listOf(
        "A week down! You're rewriting your habits.",
        "Small wins lead to big victories. Keep stacking those days.",
        "Your body is starting to heal. Feel the clarity returning."
    ),
    // 30 - 60 days: THE CRITICAL ZONE (Super Motivating)
    30 to listOf(
        "30 days is a massive milestone, but don't let your guard down now!",
        "This is where the real transformation happens. Push through the urge to settle.",
        "You've survived the first month. Now you're building a new identity.",
        "Don't trade what you want most for what you want in the moment.",
        "The 'pink cloud' might fade, but your strength is growing. Keep grinding for that 100-day gold!"
    ),
    // 60 - 100 days: The Home Stretch to Gold
    60 to listOf(
        "You are more than halfway to the 100-day Gold medal!",
        "Discipline is choosing between what you want now and what you want most.",
        "Success is the sum of small efforts repeated day in and day out."
    ),
    // 100 - 365 days: The Long Road
    100 to listOf(
        "Triple digits! You are an inspiration.",
        "Sobriety isn't just about not drinking; it's about living a life you don't need to escape.",
        "Keep going until your 'old life' feels like a distant memory."
    ),
    // 1 year - 3 years: Mastery
    365 to listOf(
        "One whole year of freedom. Look how far you've come.",
        "You've proven this is a lifestyle, not just a challenge.",
        "The 3-year Diamond is on the horizon. Keep leading by example."
    ),
    // 3 years+: Legacy
    1095 to listOf(
        "Diamond status achieved. You are a master of your own destiny.",
        "The best version of you is here to stay.",
        "Helping others is the best way to keep what you have."
    )
)

/**
 * Helper function to get a random quote based on the current streak
 */
fun getQuoteForDay(days: Int): String {
    // Find the highest key that is less than or equal to current days
    val applicableKey = SOBRIETY_QUOTES.keys
        .filter { it <= days }
        .maxOrNull() ?: 0

    val quotes = SOBRIETY_QUOTES[applicableKey] ?: listOf("Keep going!")
    return quotes.random()
}