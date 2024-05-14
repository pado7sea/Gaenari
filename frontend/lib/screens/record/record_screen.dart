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
  DateTime _focusedDay = DateTime.now(); // 달력에 오늘 날짜 표시
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
        _focusedDay.year, _focusedDay.month);
    statisticList = await RecordSevice.fetchMonthlyStatisticList(
        _focusedDay.year, _focusedDay.month);
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
    print(monthly.exerciseRecords![3].dailyRecords![0].recordDist);
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
                  SizedBox(height: 10),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 20),
                    child: _monthrecord(),
                  ),
                  SizedBox(height: 10),
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
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          Expanded(
            flex: 1,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // 총 거리
                Image(
                  image: AssetImage('assets/emoji/running.png'),
                  width: 25,
                  height: 35,
                  fit: BoxFit.cover,
                ),
                SizedBox(
                  width: 75,
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        SizedBox(height: 10),
                        Text20(
                            text: statistic.dist!.toStringAsFixed(0),
                            bold: true),
                        Text12(text: 'km')
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: 2,
            height: 60,
            color: myLightGrey,
          ),
          Expanded(
            flex: 1,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // 총 칼로리
                Image(
                  image: AssetImage('assets/emoji/fire.png'),
                  width: 25,
                  height: 30,
                  fit: BoxFit.cover,
                ),
                SizedBox(
                  width: 75,
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        SizedBox(height: 10),
                        Text20(
                            text: statistic.cal!.toStringAsFixed(0),
                            bold: true),
                        Text12(text: 'kcal')
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: 2,
            height: 60,
            color: myLightGrey,
          ),
          Expanded(
            flex: 1,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // 총 시간
                Image(
                  image: AssetImage('assets/emoji/clock.png'),
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
                SizedBox(
                  width: 75,
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        SizedBox(height: 10),
                        Text20(
                            text:
                                "${(statistic.time! / 60).floorToDouble().toInt()}",
                            bold: true),
                        Text12(
                          text: "min",
                        )
                      ],
                    ),
                  ),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  Widget _calendar() {
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
                focusedDay: _focusedDay,
                calendarFormat: CalendarFormat.month,
                availableGestures: AvailableGestures.none,
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
                },
                eventLoader: _getEventsForDay,
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
                            onTap: () => Navigator.of(context).push(
                                SlidePageRoute(
                                    nextPage: DetailRecordScreen(
                                        selectedDate: _selectedDay))),
                            title: Padding(
                              padding: const EdgeInsets.fromLTRB(16, 8, 16, 8),
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
                                        bold: true,
                                      ),
                                      SizedBox(
                                        height: 5,
                                      ),
                                      Row(
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
                                              text:
                                                  '${monthly.exerciseRecords![_selectedDay.day - 1].dailyRecords![index].recordDist!.toInt()}km',
                                              bold: true),
                                          SizedBox(width: 10),
                                          Container(
                                            width: 1,
                                            height: 25,
                                            color: myBlack,
                                          ),
                                          SizedBox(width: 10),
                                          Image(
                                            image: AssetImage(
                                                'assets/emoji/fire.png'),
                                            width: 20,
                                            height: 25,
                                            fit: BoxFit.cover,
                                          ),
                                          SizedBox(width: 5),
                                          Text16(
                                              text:
                                                  '${monthly.exerciseRecords![_selectedDay.day - 1].dailyRecords![index].recordCal!.toInt()}kcal',
                                              bold: true),
                                          SizedBox(width: 10),
                                          Container(
                                            width: 1,
                                            height: 25,
                                            color: myBlack,
                                          ),
                                          SizedBox(width: 10),
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
                                                  '${(monthly.exerciseRecords![_selectedDay.day - 1].dailyRecords![index].recordTime! / 60).toInt()}분',
                                              bold: true),
                                        ],
                                      ),
                                    ],
                                  ),
                                  Image(
                                    image: AssetImage(
                                        'assets/icons/common_front.png'),
                                    width: 20,
                                    height: 20,
                                    fit: BoxFit.cover,
                                    filterQuality: FilterQuality.none,
                                  ),
                                ],
                              ),
                            )),
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
