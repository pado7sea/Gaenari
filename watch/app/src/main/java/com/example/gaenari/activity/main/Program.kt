package com.example.gaenari.activity.main

data class Program(
    val programId: Long,
    val programTitle: String,
    val type: String,
    val usageCount: Int,
    val finishedCount: Int,
    val program: ProgramDetail
)

data class ProgramDetail(
    val intervalInfo: IntervalInfo
)

data class IntervalInfo(
    val targetValue: Int? = null,
    val duration: Int? = null,
    val setCount: Int? = null,
    val rangeCount: Int? = null,
    val ranges: List<Range>? = null
)

data class Range(
    val isRunning: Boolean,
    val time: Int,
    val speed: Int = -1
)

fun createMockData(): List<Program> {
    return listOf(
        Program(
            programId = 1,
            programTitle = "거리목표1",
            type = "D",
            usageCount = 10,
            finishedCount = 5,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 1) // 목표 거리
            )
        ),
        Program(
            programId = 2,
            programTitle = "시간목표1",
            type = "T",
            usageCount = 12,
            finishedCount = 7,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 1) // 목표 시간
            )
        ),
        Program(
            programId = 3,
            programTitle = "인터벌1",
            type = "I",
            usageCount = 15,
            finishedCount = 9,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(
                    duration = 120,
                    setCount = 3,
                    rangeCount = 2,
                    ranges = listOf(
                        Range(isRunning = true, time = 60, speed = 10),
                        Range(isRunning = false, time = 60, speed = 5)
                    )
                )
            )
        ),
        Program(
            programId = 4,
            programTitle = "인터벌2",
            type = "I",
            usageCount = 18,
            finishedCount = 11,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(
                    duration = 100,
                    setCount = 7,
                    rangeCount = 2,
                    ranges = listOf(
                        Range(isRunning = true, time = 50, speed = 8),
                        Range(isRunning = false, time = 50, speed = 6)
                    )
                )
            )
        ),
        Program(
            programId = 5,
            programTitle = "시간목표2",
            type = "T",
            usageCount = 5,
            finishedCount = 2,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 3)
            )
        ),
        Program(
            programId = 6,
            programTitle = "거리목표2",
            type = "D",
            usageCount = 8,
            finishedCount = 3,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 6)
            )
        ),
        Program(
            programId = 7,
            programTitle = "시간목표3",
            type = "T",
            usageCount = 6,
            finishedCount = 1,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 101)
            )
        ),
        Program(
            programId = 8,
            programTitle = "거리목표3",
            type = "D",
            usageCount = 9,
            finishedCount = 4,
            program = ProgramDetail(
                intervalInfo = IntervalInfo(targetValue = 8)
            )
        )
    )
}
