// ignore_for_file: use_build_context_synchronously

import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:forsythia/models/programs/program_list.dart';
import 'package:forsythia/screens/program/program_detail_screen.dart';
import 'package:forsythia/screens/program/program_distance_screen.dart';
import 'package:forsythia/screens/program/program_interval_screen.dart';
import 'package:forsythia/screens/program/program_time_screen.dart';
import 'package:forsythia/service/program_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:forsythia/widgets/slide_page_route.dart';

import 'package:forsythia/widgets/small_app_bar.dart';

class ProgramScreen extends StatefulWidget {
  const ProgramScreen({super.key});

  @override
  State<ProgramScreen> createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  bool _isExpanded = false; // 확장 여부를 나타내는 변수
  late List<ProgramItem> list = [];
  late List<ProgramItem> alist = [];
  late List<ProgramItem> tlist = [];
  late List<ProgramItem> dlist = [];
  late List<ProgramItem> ilist = [];
  late List<List<ProgramItem>> activeList = [alist, tlist, dlist, ilist];
  int activeNum = 0;
  late SlidableController slidableController =
      SlidableController(); // 슬라이드 컨트롤러 추가

  @override
  void initState() {
    super.initState();
    getList();
  }

  getList() async {
    ProgramList response = await ProgramService.fetchProgramList();
    setState(() {
      alist = response.data!;
      tlist = response.data!.where((e) => e.type == "T").toList();
      dlist = response.data!.where((e) => e.type == "D").toList();
      ilist = response.data!.where((e) => e.type == "I").toList();
      activeList = [alist, tlist, dlist, ilist];
      list = activeList[activeNum];
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '운동프로그램'),
      body: Stack(
        children: [
          Column(
            children: [
              _topButtons(),
              _programs(),
              SizedBox(
                height: 10,
              )
            ],
          ),
          // 여기서 배경을 만들어
          if (_isExpanded)
            GestureDetector(
              onTap: () {
                if (_isExpanded) {
                  setState(() {
                    _isExpanded = false;
                  });
                }
              },
              child: Container(
                color: _isExpanded
                    ? Colors.black.withOpacity(0.5)
                    : Colors.transparent,
              ),
            ),
          // 이제 원래의 내용을 넣어
        ],
      ),
      floatingActionButton: Stack(
        children: [
          AnimatedPositioned(
            duration: Duration(milliseconds: 300),
            right: _isExpanded ? 0 : 0,
            bottom: _isExpanded ? 230 : 0,
            child: GestureDetector(
              onTap: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: AddTimeProgramPage()));
                setState(() {
                  _isExpanded = !_isExpanded;
                });
              },
              child: Container(
                decoration: myActiveBoxDecoration,
                padding: EdgeInsets.all(5),
                margin: EdgeInsets.all(7),
                height: 60,
                width: 60,
                child: Column(
                  children: [
                    Image.asset(
                      "assets/emoji/clock.png",
                      width: 30,
                      height: 30,
                    ),
                    Text12(
                      text: "시간목표",
                      bold: true,
                    )
                  ],
                ),
              ),
            ),
          ),
          AnimatedPositioned(
            duration: Duration(milliseconds: 300),
            right: _isExpanded ? 0 : 0,
            bottom: _isExpanded ? 160 : 0,
            child: GestureDetector(
              onTap: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: AddDistanceProgramPage()));
                setState(() {
                  _isExpanded = !_isExpanded;
                });
              },
              child: Container(
                decoration: myYellowBoxDecoration,
                padding: EdgeInsets.all(5),
                margin: EdgeInsets.all(7),
                height: 60,
                width: 60,
                child: Column(
                  children: [
                    Image.asset(
                      "assets/emoji/running.png",
                      width: 30,
                      height: 30,
                    ),
                    Text12(
                      text: "거리목표",
                      bold: true,
                    )
                  ],
                ),
              ),
            ),
          ),
          AnimatedPositioned(
            duration: Duration(milliseconds: 300),
            right: _isExpanded ? 0 : 0,
            bottom: _isExpanded ? 90 : 0,
            child: GestureDetector(
              onTap: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: AddIntervalProgramPage()));
                setState(() {
                  _isExpanded = !_isExpanded;
                });
              },
              child: Container(
                decoration: myRedBoxDecoration,
                padding: EdgeInsets.all(5),
                margin: EdgeInsets.all(7),
                height: 60,
                width: 60,
                child: Column(
                  children: [
                    Image.asset(
                      "assets/emoji/fire.png",
                      width: 30,
                      height: 30,
                    ),
                    Text12(
                      text: "인터벌",
                      bold: true,
                    )
                  ],
                ),
              ),
            ),
          ),
          AnimatedPositioned(
            duration: Duration(milliseconds: 300),
            right: _isExpanded ? 0 : 0,
            bottom: _isExpanded ? 0 : 0,
            child: GestureDetector(
              onTap: () {
                setState(() {
                  _isExpanded = !_isExpanded;
                });
              },
              child: Container(
                decoration: myActiveBoxDecoration,
                padding: EdgeInsets.all(25),
                child: AnimatedContainer(
                  duration: Duration(milliseconds: 300), // 회전 애니메이션 지속 시간
                  transform: Matrix4.identity()
                    ..translate(!_isExpanded ? 12.2 : 12.5,
                        !_isExpanded ? -22.5 : 12.5) // 이미지의 중심으로 이동
                    ..rotateZ(_isExpanded ? 6.28 : 3.14 / 4) // Z축을 기준으로 회전
                    ..translate(!_isExpanded ? 12.5 : -12.5,
                        !_isExpanded ? 12.5 : -12.5), // 이동한 이미지를 다시 원래 위치로 이동
                  child: Image.asset(
                    "assets/icons/common_close.png",
                    width: 25,
                    height: 25,
                    filterQuality: FilterQuality.none,
                    fit: BoxFit.cover,
                  ),
                ),
              ), // 확장 상태에 따라 아이콘 변경
            ),
          ),
        ],
      ),
    );
  }

  // 상단의 버튼들
  Widget _topButtons() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(16, 20, 16, 0),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Expanded(
                  child: SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Row(
                  children: [
                    GestureDetector(
                      onTap: () {
                        setState(() {
                          activeNum = 0;
                          list = activeList[activeNum];
                        });
                      },
                      child: Container(
                        decoration: activeNum == 0
                            ? BoxDecoration(
                                color: myLightBlue,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10)))
                            : BoxDecoration(
                                color: myLightGrey,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10))),
                        padding: EdgeInsetsDirectional.fromSTEB(15, 5, 15, 5),
                        margin: EdgeInsetsDirectional.only(end: 10),
                        child: Text16(
                          text: "전체",
                          bold: true,
                        ),
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        setState(() {
                          activeNum = 1;
                          list = activeList[activeNum];
                        });
                      },
                      child: Container(
                        decoration: activeNum == 1
                            ? BoxDecoration(
                                color: myWhiteGreen,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10)))
                            : BoxDecoration(
                                color: myLightGrey,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10))),
                        padding: EdgeInsetsDirectional.fromSTEB(15, 5, 15, 5),
                        margin: EdgeInsetsDirectional.only(end: 10),
                        child: Text16(
                          text: "시간목표",
                          bold: true,
                        ),
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        setState(() {
                          activeNum = 2;
                          list = activeList[activeNum];
                        });
                      },
                      child: Container(
                        decoration: activeNum == 2
                            ? BoxDecoration(
                                color: myLightYellow,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10)))
                            : BoxDecoration(
                                color: myLightGrey,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10))),
                        padding: EdgeInsetsDirectional.fromSTEB(15, 5, 15, 5),
                        margin: EdgeInsetsDirectional.only(end: 10),
                        child: Text16(
                          text: "거리목표",
                          bold: true,
                        ),
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        setState(() {
                          activeNum = 3;
                          list = activeList[activeNum];
                        });
                      },
                      child: Container(
                        decoration: activeNum == 3
                            ? BoxDecoration(
                                color: myLightRed,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10)))
                            : BoxDecoration(
                                color: myLightGrey,
                                borderRadius:
                                    BorderRadius.all(Radius.circular(10))),
                        padding: EdgeInsetsDirectional.fromSTEB(15, 5, 15, 5),
                        margin: EdgeInsetsDirectional.only(end: 10),
                        child: Text16(
                          text: "인터벌목표",
                          bold: true,
                        ),
                      ),
                    ),
                  ],
                ),
              )),
              SizedBox(width: 8),
            ],
          ),
          SizedBox(height: 16),
        ],
      ),
    );
  }

  Widget _programs() {
    return Expanded(
      child: ShaderMask(
        shaderCallback: (Rect bounds) {
          return LinearGradient(
            begin: Alignment.center,
            end: Alignment.topCenter,
            colors: const [Colors.white, Color(0x00FFFFFF)],
            stops: const [0.95, 1],
            tileMode: TileMode.mirror,
          ).createShader(bounds);
        },
        child: ListView.builder(
          shrinkWrap: true,
          itemCount: list.length,
          itemBuilder: (BuildContext context, int index) {
            return GestureDetector(
              onTap: () {
                Navigator.of(context)
                    .push(SlidePageRoute(
                        nextPage: ProgramDetailScreen(
                            programId: list[index].programId!)))
                    .then((result) {
                  // 돌아올 때 데이터를 수신하고 처리
                  if (result == "update") {
                    // 리스트 업데이트 메서드 호출
                    getList();
                  }
                });
              },
              child: Slidable(
                controller: slidableController, // 슬라이드 컨트롤러 추가
                key: Key(list[index].programId.toString()),
                actionPane: SlidableBehindActionPane(),
                actionExtentRatio: 0.25,
                secondaryActions: [
                  GestureDetector(
                    onTap: () async {
                      await showModalBottomSheet(
                        context: context,
                        builder: (BuildContext context) {
                          return BackdropFilter(
                            filter: ImageFilter.blur(sigmaX: 3, sigmaY: 3),
                            child: ModalContent(
                              height: 250,
                              customWidget: _modal(index),
                            ),
                          );
                        },
                      );
                    },
                    child: Container(
                      decoration: myRedBoxDecoration,
                      margin: EdgeInsets.fromLTRB(0, 5, 20, 21),
                      padding: EdgeInsets.all(13),
                      child: Column(
                        children: const [
                          Image(
                            image: AssetImage("assets/icons/common_trash.png"),
                            width: 30,
                            height: 30,
                            fit: BoxFit.cover,
                            filterQuality: FilterQuality.none,
                          ),
                          SizedBox(height: 9),
                          Text12(text: "삭제")
                        ],
                      ),
                    ),
                  ),
                ],
                actions: [
                  GestureDetector(
                    onTap: () async {
                      await ProgramService.fetchFavoriteProgram(
                          list[index].programId);
                      getList();
                      slidableController.activeState?.close();
                    },
                    child: Container(
                      decoration: myYellowBoxDecoration,
                      margin: EdgeInsets.fromLTRB(20, 5, 0, 21),
                      padding: EdgeInsets.all(13),
                      child: Column(
                        children: const [
                          // Image(
                          //   image:
                          //       AssetImage("assets/color_icons/icon_star.png"),
                          //   width: 30,
                          //   height: 30,
                          //   fit: BoxFit.cover,
                          //   filterQuality: FilterQuality.none,
                          // ),
                          Icon(Icons.star_rounded, size: 30, color: myYellow),
                          SizedBox(height: 9),
                          Text12(text: "즐겨찾기")
                        ],
                      ),
                    ),
                  ),
                ],
                child: Container(
                  decoration: myBoxDecoration,
                  margin: EdgeInsets.fromLTRB(20, 0, 20, 16),
                  padding: EdgeInsets.all(10),
                  child: Row(
                    children: [
                      Container(
                        padding: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          // color: myWhiteGreen,
                          borderRadius: BorderRadius.all(Radius.circular(10)),
                        ),
                        child: Image(
                          image: AssetImage("assets/emoji/running.png"),
                          height: 60,
                          width: 60,
                          fit: BoxFit.cover,
                          filterQuality: FilterQuality.none,
                        ),
                      ),
                      SizedBox(
                        width: 8,
                      ),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisAlignment: MainAxisAlignment.end,
                              children: [
                                Expanded(
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      Text16(
                                        text: list[index].programTitle!,
                                        bold: true,
                                      ),
                                      SizedBox(height: 2),
                                      Text12(
                                        text: list[index].type == "T"
                                            ? "${((list[index].program!.targetValue)! ~/ 60).toInt()}분 목표"
                                            : list[index].type == "D"
                                                ? "${(list[index].program!.targetValue)}km 목표"
                                                : "세트 당 ${((list[index].program!.intervalInfo!.duration)! ~/ 60).toInt().toString()}분 | 총 ${list[index].program!.intervalInfo!.setCount}세트 목표",
                                        textColor: myGrey,
                                      ),
                                    ],
                                  ),
                                ),
                                // Image.asset(
                                //   list[index].isFavorite!
                                //       ? "assets/color_icons/icon_star.png"
                                //       : "assets/color_icons/icon_nonestar.png",
                                //   width: 40,
                                //   height: 40,
                                //   fit: BoxFit.cover,
                                //   filterQuality: FilterQuality.none,
                                // ),
                                list[index].isFavorite!
                                    ? Icon(Icons.star_rounded,
                                        size: 40, color: myYellow)
                                    : Icon(Icons.star_border_rounded,
                                        size: 40, color: myYellow),
                                SizedBox(width: 10),
                              ],
                            ),
                            SizedBox(height: 7),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Container(
                                  decoration: list[index].type == "T"
                                      ? myGreenBoxDecoration
                                      : list[index].type == "D"
                                          ? myYellowBoxDecoration
                                          : myRedBoxDecoration,
                                  child: Padding(
                                    padding: EdgeInsetsDirectional.fromSTEB(
                                        10, 2, 10, 2),
                                    child: Text12(
                                      text: list[index].type == "T"
                                          ? "시간목표"
                                          : list[index].type == "D"
                                              ? "거리목표"
                                              : "인터벌목표",
                                      bold: true,
                                    ),
                                  ),
                                ),
                                Row(
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children: [
                                    Text16(
                                      text: "달성 ${list[index].finishedCount}회",
                                      bold: true,
                                    ),
                                    Text12(
                                        text: "/총 ${list[index].usageCount}회")
                                  ],
                                )
                              ],
                            )
                          ],
                        ),
                      )
                    ],
                  ),
                ),
              ),
            );
          },
        ),
      ),
    );
  }

  Widget _modal(index) {
    return Column(
      children: [
        SizedBox(height: 20),
        Text16(
          text: '${list[index].programTitle}을(를)',
          bold: true,
        ),
        Text16(
          text: ' 프로그램목록에서 삭제하시겠어요?',
          bold: true,
        ),
        SizedBox(height: 20),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SmallButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              text: "취소",
              active: false,
              widthPadding: 50,
            ),
            SizedBox(width: 16),
            SmallButton(
              onPressed: () async {
                await ProgramService.fetchDeleteProgram(list[index].programId);
                getList();
                Navigator.of(context).pop();
              },
              text: "삭제",
              active: true,
              widthPadding: 50,
            ),
          ],
        ),
      ],
    );
  }
}
