import 'package:flutter/material.dart';
import 'package:forsythia/models/programs/program_detail.dart';
import 'package:forsythia/service/program_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';

import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:intl/intl.dart';

class ProgramDetailScreen extends StatefulWidget {
  final int programId;

  const ProgramDetailScreen({super.key, required this.programId});

  @override
  State<ProgramDetailScreen> createState() => _ProgramDetailScreenState();
}

class _ProgramDetailScreenState extends State<ProgramDetailScreen> {
  late Detail programDetail = Detail();

  @override
  void initState() {
    super.initState();
    getDetail();
  }

  getDetail() async {
    ProgramDetail response =
        await ProgramService.fetchProgramDetail(widget.programId);
    setState(() {
      programDetail = response.data!;
    });
    print(programDetail.programTitle);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '운동프로그램 상세',
        back: true,
      ),
      body: programDetail.programId != null
          ? SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(
                      height: 10,
                    ),
                    _title(),
                    SizedBox(height: 30),
                    programDetail.type == "I"
                        ? SizedBox(
                            width: double.infinity,
                            height: 150,
                            child: _graph())
                        : SizedBox(
                            height: 0,
                          ),
                    SizedBox(height: programDetail.type == "I" ? 30 : 0),
                    programDetail.usageCount != 0
                        ? Text20(
                            text: "프로그램 기록",
                            bold: true,
                          )
                        : SizedBox(
                            height: 0,
                          ),
                    _monthrecord(),
                    SizedBox(height: 30),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        Text20(
                          text: "프로그램 달성 ${programDetail.finishedCount}회",
                          bold: true,
                        ),
                        Text16(text: " / 총 ${programDetail.usageCount}회")
                      ],
                    ),
                    SizedBox(height: 20),
                    _list(),
                  ],
                ),
              ),
            )
          : Text(""),
    );
  }

  Widget _title() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Text25(
                  text: programDetail.programTitle!,
                  bold: true,
                ),
                SizedBox(width: 10),
                Container(
                  decoration: programDetail.type == "T"
                      ? myActiveBoxDecoration
                      : programDetail.type == "D"
                          ? myYellowBoxDecoration
                          : myRedBoxDecoration,
                  child: Padding(
                    padding: EdgeInsetsDirectional.fromSTEB(10, 2, 10, 2),
                    child: Text12(
                      text: programDetail.type == "T"
                          ? "시간목표"
                          : programDetail.type == "D"
                              ? "거리목표"
                              : "인터벌목표",
                      bold: true,
                    ),
                  ),
                ),
              ],
            ),
            SizedBox(
              height: 3,
            ),
            Text12(
              text: programDetail.type == "T"
                  ? "${((programDetail.program!.targetValue)! ~/ 60).toInt()}분 목표"
                  : programDetail.type == "D"
                      ? "${(programDetail.program!.targetValue)}km 목표"
                      : "세트 당 ${((programDetail.program!.intervalInfo!.duration)! ~/ 60).toInt().toString()}분 | 총 ${programDetail.program!.intervalInfo!.setCount}세트 목표",
              textColor: myGrey,
            ),
          ],
        ),
        GestureDetector(
          onTap: () async {
            await ProgramService.fetchFavoriteProgram(programDetail.programId);
            setState(() {
              programDetail.isFavorite = !programDetail.isFavorite!;
            });
          },
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Image.asset(
              programDetail.isFavorite!
                  ? "assets/color_icons/icon_star.png"
                  : "assets/color_icons/icon_nonestar.png",
              width: 40,
              height: 40,
              filterQuality: FilterQuality.none,
              fit: BoxFit.cover,
            ),
          ),
        )
      ],
    );
  }

  Widget _graph() {
    return ListView.builder(
      scrollDirection: Axis.horizontal, // 리스트뷰를 가로 방향으로 스크롤하도록 설정
      itemCount: programDetail.program!.intervalInfo!.setCount! *
          programDetail.program!.intervalInfo!.rangeCount!, // 리스트 아이템 개수
      itemBuilder: (BuildContext context, int index) {
        return Column(
          crossAxisAlignment: CrossAxisAlignment.end,
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Container(
              width: programDetail
                      .program!
                      .intervalInfo!
                      .ranges![index %
                          programDetail.program!.intervalInfo!.rangeCount!]
                      .time! /
                  15, // 각 아이템의 가로 크기
              height: programDetail
                      .program!
                      .intervalInfo!
                      .ranges![index %
                          programDetail.program!.intervalInfo!.rangeCount!]
                      .speed! *
                  4,
              color: Colors.blueAccent,
              margin: EdgeInsets.all(2),
            ),
          ],
        );
      },
    );
  }

  Widget _section() {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: 8),
      width: 2,
      height: 15,
      decoration: BoxDecoration(color: myGrey),
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
                            text: programDetail.totalRecord!.distance!
                                .toStringAsFixed(0),
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
                            text: programDetail.totalRecord!.cal!
                                .toStringAsFixed(0),
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
                                "${(programDetail.totalRecord!.time! / 60).floorToDouble().toInt()}",
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

  Widget _list() {
    return ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: programDetail.usageLog!.length,
        itemBuilder: (BuildContext context, int index) {
          return Container(
            margin: EdgeInsets.fromLTRB(8, 0, 8, 10),
            padding: EdgeInsets.fromLTRB(16, 8, 16, 8),
            decoration: programDetail.usageLog![index].isFinished!
                ? myBoxDecoration
                : myNoneBoxDecoration,
            child: Row(
              children: [
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text16(
                        text: DateFormat("y년 M월 d일").format(DateTime.parse(
                            programDetail.usageLog![index].date!)),
                        bold: true,
                      ),
                      SizedBox(height: 10),
                      Row(
                        children: [
                          Text12(
                            text:
                                "${programDetail.usageLog![index].distance!.toInt().toString()}km",
                            bold: true,
                            textColor: myBlue,
                          ),
                          _section(),
                          Text12(
                            text:
                                "${programDetail.usageLog![index].cal!.toInt().toString()}kcal",
                            bold: true,
                            textColor: myRed,
                          ),
                          _section(),
                          Text12(
                            text:
                                "${(programDetail.usageLog![index].time! / 60).floorToDouble().toInt().toString()}분",
                            bold: true,
                            textColor: myYellow,
                          ),
                          _section(),
                          Text12(
                            text:
                                "${(programDetail.usageLog![index].averagePace! / 60).floorToDouble().toInt().toString()}'${(programDetail.usageLog![index].averagePace! % 60).floorToDouble().toInt().toString()}''/km",
                            bold: true,
                            textColor: myMainGreen,
                          ),
                        ],
                      )
                    ],
                  ),
                ),
                Text16(
                  text:
                      programDetail.usageLog![index].isFinished! ? "달성" : "미달성",
                  bold: true,
                  textColor: programDetail.usageLog![index].isFinished!
                      ? myMainGreen
                      : myRed,
                )
              ],
            ),
          );
        });
  }
}
