import 'package:flutter/material.dart';
import 'package:forsythia/models/records/weekly_statistic_list.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';

class WeeklyStatisticsWidget extends StatefulWidget {
  final List<Weekly> weekly;

  const WeeklyStatisticsWidget({
    super.key,
    required this.weekly,
  });

  @override
  State<WeeklyStatisticsWidget> createState() => _WeeklyStatisticsWidgetState();
}

class _WeeklyStatisticsWidgetState extends State<WeeklyStatisticsWidget> {
  int activeNum = 0;
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 200,
      width: double.infinity,
      padding: EdgeInsets.all(10),
      margin: EdgeInsets.only(bottom: 16),
      decoration:
          myBoxDecoration, // 이 부분은 해당 위젯에서 사용하는 BoxDecoration에 맞게 수정해야 합니다.
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text16(
                text: "주간통계",
                bold: true,
              ),
              Row(
                children: [
                  _buildStatisticButton(0, "운동시간"),
                  SizedBox(width: 5),
                  _buildStatisticButton(1, "소모칼로리"),
                  SizedBox(width: 5),
                  _buildStatisticButton(2, "운동거리"),
                ],
              )
            ],
          ),
          SizedBox(height: 15),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: List.generate(
              widget.weekly.length,
              (index) => _weeklyGraph(index),
            ),
          )
        ],
      ),
    );
  }

  Widget _buildStatisticButton(int index, String text) {
    return GestureDetector(
      onTap: () {
        setState(() {
          activeNum = index; // 선택된 통계 유형을 변경
        });
      },
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(5)),
          color: activeNum == index ? myLightGreen : myLightGrey,
        ),
        padding: EdgeInsets.all(4),
        child: Text16(
          text: text,
          textColor: activeNum == index ? Colors.white : myBlack,
        ),
      ),
    );
  }

  Widget _weeklyGraph(int i) {
    return SizedBox(
      height: 125,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text16(
            text: widget.weekly[i].date!.split("-")[2],
            bold: true,
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.end,
            crossAxisAlignment: CrossAxisAlignment.end,
            children: [
              Column(
                children: [
                  Container(
                    color: mygradient[activeNum == 0
                        ? myTime(widget.weekly[i].dailyStatistic!.time!)
                        : activeNum == 1
                            ? myCal(widget.weekly[i].dailyStatistic!.cal!)
                            : myDist(widget.weekly[i].dailyStatistic!.dist!)],
                    width: 30,
                    height: widget.weekly[i].dailyStatistic!.time! != 0.0
                        ? (activeNum == 0
                                ? (myTime(widget
                                            .weekly[i].dailyStatistic!.time!) +
                                        1) *
                                    16
                                : activeNum == 1
                                    ? (myCal(widget.weekly[i].dailyStatistic!
                                                .cal!) +
                                            1) *
                                        16
                                    : (myDist(widget.weekly[i].dailyStatistic!
                                                .dist!) +
                                            1) *
                                        16)
                            .toDouble()
                        : 0,
                  ),
                  SizedBox(height: 3),
                  Text12(
                      text:
                          "${(activeNum == 0 ? widget.weekly[i].dailyStatistic!.time! / 60 : activeNum == 1 ? widget.weekly[i].dailyStatistic!.cal! : widget.weekly[i].dailyStatistic!.dist!).toInt()}${activeNum == 0 ? "분" : activeNum == 1 ? "kcal" : "km"}")
                ],
              )
            ],
          )
        ],
      ),
    );
  }

  int myTime(time) {
    int num = 0;
    if (time > 3600.0) {
      num = 4;
    } else if (time > 2400.0) {
      num = 3;
    } else if (time > 1200.0) {
      num = 2;
    } else if (time > 600.0) {
      num = 1;
    } else if (time <= 600.0) {
      num = 0;
    }

    return num;
  }

  int myCal(cal) {
    int num = 0;
    if (cal > 1000.0) {
      num = 4;
    } else if (cal > 750.0) {
      num = 3;
    } else if (cal > 500.0) {
      num = 2;
    } else if (cal > 250.0) {
      num = 1;
    } else if (cal <= 250.0) {
      num = 0;
    }

    return num;
  }

  int myDist(dist) {
    int num = 0;
    if (dist > 20.0) {
      num = 4;
    } else if (dist > 15.0) {
      num = 3;
    } else if (dist > 10.0) {
      num = 2;
    } else if (dist > 5.0) {
      num = 1;
    } else if (dist <= 5.0) {
      num = 0;
    }

    return num;
  }
}
