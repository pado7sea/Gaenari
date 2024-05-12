// ignore_for_file: prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'package:forsythia/screens/record/detail_record_screen.dart';
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

  // 이벤트 추가
  Map<DateTime, List<Event>> events = {
    DateTime.utc(2024, 5, 9): [
      Event('운동하기'),
      Event('점심 약속'),
      Event('점심 약속'),
      Event('점심 약속'),
      Event('점심 약속')
    ],
    DateTime.utc(2024, 5, 10): [Event('수영 수업'), Event('영화 보기')],
  };

  // 리스트에서 받을 이벤트
  late final ValueNotifier<List<Event>> _selectedEvents;

  @override
  void initState() {
    super.initState();
    _selectedDay = _focusedDay;
    _selectedEvents = ValueNotifier(_getEventsForDay(_selectedDay));
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
      body: SingleChildScrollView(
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
      ),
    );
  }

  Widget _monthrecord() {
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
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 거리')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myGrey,
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
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 칼로리')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myGrey,
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
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 시간')
                ],
              ),
            ),
          ],
        )
      ],
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
                            onTap: () => Navigator.of(context).push(
                                SlidePageRoute(
                                    nextPage: DetailRecordScreen(
                                        selectedDate: _selectedDay))),
                            title: Padding(
                              padding: const EdgeInsets.fromLTRB(20, 10, 0, 10),
                              child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                children: [
                                  Image(
                                    image:
                                        AssetImage('assets/emoji/running.png'),
                                    width: 20,
                                    height: 30,
                                    fit: BoxFit.cover,
                                  ),
                                  SizedBox(width: 5),
                                  Text16(text: '10.9', bold: true),
                                  SizedBox(width: 10),
                                  Container(
                                    width: 1,
                                    height: 25,
                                    color: myBlack,
                                  ),
                                  SizedBox(width: 10),
                                  Image(
                                    image: AssetImage('assets/emoji/fire.png'),
                                    width: 20,
                                    height: 25,
                                    fit: BoxFit.cover,
                                  ),
                                  SizedBox(width: 5),
                                  Text16(text: '160', bold: true),
                                  SizedBox(width: 10),
                                  Container(
                                    width: 1,
                                    height: 25,
                                    color: myBlack,
                                  ),
                                  SizedBox(width: 10),
                                  Image(
                                    image: AssetImage('assets/emoji/clock.png'),
                                    width: 23,
                                    height: 23,
                                    fit: BoxFit.cover,
                                  ),
                                  SizedBox(width: 5),
                                  Text16(text: ' 78', bold: true),
                                  SizedBox(width: 30),
                                  Image(
                                    image: AssetImage(
                                        'assets/icons/common_front.png'),
                                    width: 20,
                                    height: 20,
                                    fit: BoxFit.cover,
                                  ),
                                ],
                              ),
                            )
                            // Text('${value[index]}'),
                            ),
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
