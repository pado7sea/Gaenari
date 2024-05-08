import 'package:flutter/material.dart';
import 'package:forsythia/screens/program/program_distance_screen.dart';
import 'package:forsythia/screens/program/program_interval_screen.dart';
import 'package:forsythia/screens/program/program_time_screen.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class AddProgramPage extends StatefulWidget {
  const AddProgramPage({super.key});

  @override
  State<AddProgramPage> createState() => _AddProgramPageState();
}

class _AddProgramPageState extends State<AddProgramPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
          title: "운동프로그램 추가",
          back: true,
        ),
        body: Column(children: [
          SizedBox(height: 20), // 검색창과 텍스트 사이의 간격
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 20), // 검색창 좌우 마진
          ),
          SizedBox(height: 16),
          GestureDetector(
            onTap: () {
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: AddTimeProgramPage()));
            },
            child: Container(
              padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  Text16(
                    text: "시간목표 ",
                  ),
                  Image.asset(
                    "assets/icons/mate_add.png",
                    filterQuality: FilterQuality.none,
                    width: 25,
                    height: 25,
                    fit: BoxFit.cover,
                  ),
                ],
              ),
            ),
          ),
          GestureDetector(
            onTap: () {
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: AddDistanceProgramPage()));
            },
            child: Container(
              padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  Text16(
                    text: "거리목표 ",
                  ),
                  Image.asset(
                    "assets/icons/mate_add.png",
                    filterQuality: FilterQuality.none,
                    width: 25,
                    height: 25,
                    fit: BoxFit.cover,
                  ),
                ],
              ),
            ),
          ),
          GestureDetector(
            onTap: () {
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: AddIntervalProgramPage()));
            },
            child: Container(
              padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  Text16(
                    text: "인터발 ",
                  ),
                  Image.asset(
                    "assets/icons/mate_add.png",
                    filterQuality: FilterQuality.none,
                    width: 25,
                    height: 25,
                    fit: BoxFit.cover,
                  ),
                ],
              ),
            ),
          ),
          SizedBox(height: 20),
        ]));
  }
}
