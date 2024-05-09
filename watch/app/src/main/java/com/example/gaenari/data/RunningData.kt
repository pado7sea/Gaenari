package com.example.gaenari.data

data class RunningData(
    var date: String,
    var exerciseType: String, // enum 타입을 문자열로 나타냄 (W, R, P 등)
    var programType: String, // enum 타입을 문자열로 나타냄 (D, T, I 등)
    var program: Program?, // exerciseType에 따라 null 가능
    var record: Record,
    var speeds: Speeds,
    var heartrates: HeartRates
) {
    data class Program(
        var programId: Long,
        var targetValue: Int?, // programType에 따라 null 가능
        var intervalInfo: IntervalInfo? // programType에 따라 null 가능
    ) {
        data class IntervalInfo(
            var duration: Int, // 인터벌 총 소요 시간
            var setCount: Int, // 세트 수
            var rangeCount: Int, // 세트 당 구간 수
            var ranges: List<Range>
        ) {
            data class Range(
                var id: Int,
                var isRunning: Boolean,
                var time: Int, // 단위: sec
                var speed: Double // 단위: km/h
            )
        }
    }

    data class Record(
        var distance: Double, // 누적 거리 단위: km
        var time: Long // 누적 시간 단위: sec
    )

    data class Speeds(
        var average: Double, // 총 평균 속도
        var arr: MutableList<Int> = mutableListOf() // 분당 속도 평균 리스트 (가변)
    )

    data class HeartRates(
        var average: Int, // 총 평균 심박수
        var arr: MutableList<Int> = mutableListOf() // 분당 평균 심박수 리스트 (가변)
    )
}