// ignore_for_file: prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'package:forsythia/models/records/record_list.dart';
import 'package:forsythia/models/records/statistic_list.dart';
import 'package:forsythia/screens/record/detail_record_screen.dart';
import 'package:forsythia/service/record_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:table_calendar/table_calendar.dart';

class RecordScreen extends StatefulWidget {
  const RecordScreen({super.key});

  @override
  State<RecordScreen> createState() => _RecordScreenState();
}

class _RecordScreenState extends State<RecordScreen> {
  DateTime? _focusedDay = DateTime.now(); // 달력에 오늘 날짜 표시
  DateTime _selectedDay = DateTime.now(); // 선택한 날짜 표시
  Monthly monthly = Monthly();
  Statistic statistic = Statistic();
  bool active = false;

  // 이벤트 추가
  Map<DateTime, List<Event>> events = {};

  // 리스트에서 받을 이벤트
  late final ValueNotifier<List<Event>> _selectedEvents;

  @override
  void initState() {
    super.initState();
    monthlyRecordList();

    // _selectedDay = _focusedDay;
    _selectedEvents = ValueNotifier(_getEventsForDay(_selectedDay));
  }

  void monthlyRecordList() async {
    MonthlyRecordList recordList;
    StatisticList statisticList;

    recordList = await RecordSevice.fetchMonthlyRecordList(
        context, _focusedDay!.year, _focusedDay!.month);
    statisticList = await RecordSevice.fetchMonthlyStatisticList(
        context, _focusedDay!.year, _focusedDay!.month);
    setState(() {
      monthly = recordList.data!;
      statistic = statisticList.data!;
      active = true;
      for (int i = 0; i < monthly.exerciseRecords!.length; i++) {
        DateTime newDate = DateTime.utc(
            monthly.year!, monthly.month!, monthly.exerciseRecords![i].day!);
        List<Event> newEvents = [];
        for (int j = 0;
            j < monthly.exerciseRecords![i].dailyRecords!.length;
            j++) {
          newEvents.add(Event("$j"));
        }
        events[newDate] = newEvents;
      }
    });
    // print(recordList.data!.ex);
    // print(statisticList.data!.dist);
    print('기록기ㅗㄹ기고리ㅗㅓㅁ나ㅣ과ㅣ; ');
    // print(monthly.exerciseRecords![3].dailyRecords![0].recordId);
    // print(_focusedDay.year);
    // print(_focusedDay.month);
  }

  @override
  void dispose() {
    // _selectedEvents.dispose();
    super.dispose();
  }

  void _onDaySelected(DateTime selectedDay, DateTime focusedDay) {
    setState(() {
      _selectedDay = selectedDay;
      _focusedDay = focusedDay;
    });
    _selectedEvents.value = _getEventsForDay(selectedDay);
  }

  List<Event> _getEventsForDay(DateTime day) {
    // 선택된 날의 모든 이벤트를 가져옴
    return events[day] ?? [];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '기록',
      ),
      body: active
          ? SingleChildScrollView(
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 20),
                    child: _monthrecord(),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 20),
                    child: _calendar(),
                  ),
                ],
              ),
            )
          : Center(
              child: CircularProgressIndicator(),
            ),
    );
  }

  Widget _monthrecord() {
    String format(double? num) {
      if (num == null) {
        return 'N/A';
      } else if (num < 0.1) {
        return num.toStringAsFixed(0);
      } else if (num >= 100) {
        return num.toStringAsFixed(0); // 정수 부분만
      } else if (num >= 10) {
        return num.toStringAsFixed(1); // 소수점 첫째 자리까지
      } else {
        return num.toStringAsFixed(2); // 소수점 둘째 자리까지
      }
    }

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      crossAxisAlignment: CrossAxisAlignment.end,
      children: [
        Row(
          children: [
            // 총 거리
            Image(
              image: AssetImage('assets/emoji/running.png'),
              width: 25,
              height: 35,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  SizedBox(height: 10),
                  Text20(
                    text: statistic.dist! >= 9999
                        ? '9999'
                        : format(statistic.dist!),
                    bold: true,
                  ),
                  Text12(text: 'km')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myLightGrey,
        ),
        Row(
          children: [
            // 총 칼로리
            Image(
              image: AssetImage('assets/emoji/fire.png'),
              width: 25,
              height: 30,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  SizedBox(height: 10),
                  Text20(
                      text: statistic.cal! >= 9999
                          ? '9999'
                          : format(statistic.cal!),
                      bold: true),
                  Text12(text: 'kcal')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myLightGrey,
        ),
        Row(
          children: [
            // 총 시간
            Image(
              image: AssetImage('assets/emoji/clock.png'),
              width: 25,
              height: 25,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  SizedBox(height: 10),
                  Text20(
                      text: "${(statistic.time! / 60).floorToDouble().toInt()}",
                      bold: true),
                  Text12(
                    text: "min",
                  )
                ],
              ),
            ),
          ],
        )
      ],
    );
  }

  Widget _calendar() {
    String format(double? num) {
      if (num == null) {
        return 'N/A';
      } else if (num < 0.1) {
        return num.toStringAsFixed(0);
      } else if (num >= 100) {
        return num.toStringAsFixed(0); // 정수 부분만
      } else if (num >= 10) {
        return num.toStringAsFixed(1); // 소수점 첫째 자리까지
      } else {
        return num.toStringAsFixed(2); // 소수점 둘째 자리까지
      }
    }

    return Center(
      child: Column(
        children: [
          ListView(
            shrinkWrap: true,
            physics: NeverScrollableScrollPhysics(),
            children: [
              TableCalendar(
                headerStyle: HeaderStyle(
                    formatButtonVisible: false,
                    headerMargin: EdgeInsets.symmetric(vertical: 10),
                    titleCentered: true,
                    titleTextStyle:
                        TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                rowHeight: 55, // 각 날짜 행의 높이 설정
                locale: 'ko-KR', // 로케일 설정 (한국어)
                firstDay: DateTime.utc(2024, 1, 1),
                lastDay: DateTime.utc(2030, 3, 14),
                focusedDay: _focusedDay!,
                calendarFormat: CalendarFormat.month,
                availableGestures: AvailableGestures.none,
                daysOfWeekHeight: 20,
                // 달력 스타일
                calendarStyle: CalendarStyle(
                    markersAlignment: Alignment.bottomCenter,
                    canMarkersOverflow: true,
                    todayDecoration: BoxDecoration(
                      color: myWhiteGreen,
                      shape: BoxShape.circle,
                    ),
                    selectedDecoration: BoxDecoration(
                      color: myLightYellow,
                      shape: BoxShape.circle,
                    ),
                    selectedTextStyle:
                        TextStyle(color: myYellow, fontWeight: FontWeight.bold),
                    todayTextStyle: TextStyle(
                        color: myMainGreen, fontWeight: FontWeight.bold),
                    weekendTextStyle: TextStyle(color: myRed),
                    // 마커스타일
                    markerSize: 7,
                    markersMaxCount: 3,
                    markersAnchor: 1,
                    markerMargin: EdgeInsets.symmetric(horizontal: 2),
                    markerDecoration: BoxDecoration(
                        color: myMainGreen, shape: BoxShape.circle)),
                // 선택된 날짜
                selectedDayPredicate: (day) {
                  return isSameDay(_selectedDay, day);
                },
                onDaySelected: _onDaySelected,
                onPageChanged: (focusedDay) {
                  _focusedDay = focusedDay;
                  _onDaySelected(focusedDay, focusedDay);
                  monthlyRecordList();
                },
                eventLoader: _getEventsForDay,
                pageJumpingEnabled: true,
              ),
            ],
          ),
          SizedBox(height: 10),
          ValueListenableBuilder<List<Event>>(
              valueListenable: _selectedEvents,
              builder: (context, value, _) {
                return ListView.builder(
                    shrinkWrap: true,
                    physics: NeverScrollableScrollPhysics(),
                    itemCount: value.length,
                    itemBuilder: (context, index) {
                      return Container(
                        margin: EdgeInsets.symmetric(vertical: 5),
                        decoration: BoxDecoration(
                            color: myWhiteGreen,
                            borderRadius: BorderRadius.circular(10)),
                        child: ListTile(
                            contentPadding: EdgeInsets.all(0),
                            onTap: () =>
                                Navigator.of(context).push(SlidePageRoute(
                                    nextPage: DetailRecordScreen(
                                  recordId: monthly
                                      .exerciseRecords![_selectedDay.day - 1]
                                      .dailyRecords![index]
                                      .recordId,
                                ))),
                            title: Padding(
                                padding:
                                    const EdgeInsets.fromLTRB(16, 8, 16, 8),
                                child: Stack(
                                  children: [
                                    SingleChildScrollView(
                                      scrollDirection: Axis.horizontal,
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        children: [
                                          Column(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
                                            children: [
                                              Text12(
                                                text: monthly
                                                    .exerciseRecords![
                                                        _selectedDay.day - 1]
                                                    .dailyRecords![index]
                                                    .recordDate!
                                                    .split("T")[1]
                                                    .split(".")[0],
                                              ),
                                              SizedBox(
                                                height: 5,
                                              ),
                                              Row(
                                                crossAxisAlignment:
                                                    CrossAxisAlignment.end,
                                                children: [
                                                  Image(
                                                    image: AssetImage(
                                                        'assets/emoji/running.png'),
                                                    width: 20,
                                                    height: 30,
                                                    fit: BoxFit.cover,
                                                  ),
                                                  SizedBox(width: 5),
                                                  Text16(
                                                      text: monthly
                                                                  .exerciseRecords![
                                                                      _selectedDay
                                                                              .day -
                                                                          1]
                                                                  .dailyRecords![
                                                                      index]
                                                                  .recordDist! >=
                                                              9999
                                                          ? '9999'
                                                          : format(monthly
                                                              .exerciseRecords![
                                                                  _selectedDay
                                                                          .day -
                                                                      1]
                                                              .dailyRecords![
                                                                  index]
                                                              .recordDist!),
                                                      bold: true),
                                                  Text12(
                                                      text: ' km', bold: true),
                                                  SizedBox(width: 15),
                                                  Container(
                                                    width: 1,
                                                    height: 25,
                                                    color: myBlack,
                                                  ),
                                                  SizedBox(width: 15),
                                                  Image(
                                                    image: AssetImage(
                                                        'assets/emoji/fire.png'),
                                                    width: 20,
                                                    height: 25,
                                                    fit: BoxFit.cover,
                                                  ),
                                                  SizedBox(width: 5),
                                                  Text16(
                                                      text: monthly
                                                                  .exerciseRecords![
                                                                      _selectedDay
                                                                              .day -
                                                                          1]
                                                                  .dailyRecords![
                                                                      index]
                                                                  .recordCal! >=
                                                              9999
                                                          ? '9999'
                                                          : format(monthly
                                                              .exerciseRecords![
                                                                  _selectedDay
                                                                          .day -
                                                                      1]
                                                              .dailyRecords![
                                                                  index]
                                                              .recordCal!),
                                                      bold: true),
                                                  Text12(
                                                      text: ' kcal',
                                                      bold: true),
                                                  SizedBox(width: 15),
                                                  Container(
                                                    width: 1,
                                                    height: 25,
                                                    color: myBlack,
                                                  ),
                                                  SizedBox(width: 15),
                                                  Image(
                                                    image: AssetImage(
                                                        'assets/emoji/clock.png'),
                                                    width: 23,
                                                    height: 23,
                                                    fit: BoxFit.cover,
                                                  ),
                                                  SizedBox(width: 5),
                                                  Text16(
                                                      text:
                                                          '${monthly.exerciseRecords![_selectedDay.day - 1].dailyRecords![index].recordTime! ~/ 60}',
                                                      bold: true),
                                                  Text12(
                                                      text: ' min', bold: true),
                                                ],
                                              ),
                                            ],
                                          ),
                                          // 여기에 여분의 공간을 추가하여 common_front.png 아이콘의 오른쪽에 여유를 둡니다.
                                          SizedBox(width: 60),
                                        ],
                                      ),
                                    ),
                                    Positioned(
                                      right: 0,
                                      child: Image(
                                        image: AssetImage(
                                            'assets/icons/common_front.png'),
                                        width: 15,
                                        height: 15,
                                        fit: BoxFit.cover,
                                        filterQuality: FilterQuality.none,
                                      ),
                                    ),
                                  ],
                                ))),
                      );
                    });
              }),
        ],
      ),
    );
  }
}

class Event {
  final String title;
  const Event(this.title);
  @override
  String toString() => title;
}
