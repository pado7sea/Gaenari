import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:forsythia/models/programs/program_list.dart';
import 'package:forsythia/screens/program/program_add_screen.dart';
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
  late List<ProgramItem> list = [];

  @override
  void initState() {
    super.initState();
    getList();
  }

  getList() async {
    ProgramList response = await ProgramService.fetchProgramList();
    setState(() {
      list = response.data!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '운동프로그램'),
      body: Column(
        children: [
          _topButtons(),
          SizedBox(height: 16),
          _programs(),
        ],
      ),
    );
  }

  // 상단의 버튼들
  Widget _topButtons() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        GestureDetector(
          onTap: () {
            Navigator.of(context)
                .push(SlidePageRoute(nextPage: AddProgramPage()))
                .then((result) {
              // 돌아올 때 데이터를 수신하고 처리
              if (result == "update") {
                // 리스트 업데이트 메서드 호출
                getList();
              }
            });
          },
          child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Text16(
                  text: "요청 ",
                ),
                Image.asset(
                  "assets/icons/mate_new.png",
                  filterQuality: FilterQuality.none,
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
        ),
        SizedBox(width: 8),
      ],
    );
  }

  Widget _programs() {
    return Expanded(
        child: ListView.builder(
            shrinkWrap: true, // 필요한 만큼의 공간만 차지하도록 설정
            itemCount: list.length,
            itemBuilder: (BuildContext context, int index) {
              return Slidable(
                actionPane: SlidableBehindActionPane(), // 슬라이드 액션 패널 설정
                actionExtentRatio: 0.25, // 슬라이드 액션의 크기 비율 설정
                secondaryActions: [
                  // 오른쪽으로 슬라이드했을 때 보여질 액션들
                  GestureDetector(
                    onTap: () async {
                      showModalBottomSheet(
                        context: context,
                        builder: (BuildContext context) {
                          return BackdropFilter(
                            filter: ImageFilter.blur(
                                sigmaX: 3, sigmaY: 3), // 블러 효과 설정
                            child: ModalContent(
                              height: 250,
                              customWidget: _contant(index),
                            ),
                          );
                        },
                      );
                    },
                    child: Container(
                      decoration: myRedBoxDecoration,
                      margin: EdgeInsets.fromLTRB(0, 5, 20, 21),
                      padding: EdgeInsets.all(10),
                      child: Column(children: const [
                        Image(
                          image: AssetImage("assets/icons/common_trash.png"),
                          width: 30,
                          height: 30,
                          fit: BoxFit.cover,
                          filterQuality: FilterQuality.none,
                        ),
                        SizedBox(height: 12),
                        Text12(text: "삭제")
                      ]),
                    ),
                  ),
                ],
                child: Container(
                    decoration: myBoxDecoration,
                    margin: EdgeInsets.fromLTRB(20, 0, 20, 16),
                    padding: EdgeInsets.all(10),
                    child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Row(
                            children: [
                              Container(
                                padding: EdgeInsets.all(5),
                                decoration: BoxDecoration(
                                    color: myWhiteGreen,
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(10))),
                                child: Image(
                                  image: AssetImage(
                                      "assets/icons/common_close.png"),
                                  height: 60,
                                  width: 60,
                                  fit: BoxFit.cover,
                                  filterQuality: FilterQuality.none,
                                ),
                              ),
                              SizedBox(
                                width: 16,
                              ),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text16(
                                    text: list[index].programTitle!,
                                    bold: true,
                                  ),
                                  SizedBox(height: 10),
                                ],
                              )
                            ],
                          ),
                          GestureDetector(
                            onTap: () {
                              // 친구 집 입장하는 어쩌구
                            },
                            child: Container(
                                width: 70,
                                padding: EdgeInsets.all(5),
                                decoration: BoxDecoration(
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(10)),
                                    border: Border.all(color: myLightGrey)),
                                child: Column(children: const [
                                  Image(
                                    image: AssetImage(
                                        "assets/icons/mate_waitfriend.png"),
                                    width: 30,
                                    height: 30,
                                    fit: BoxFit.cover,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(height: 10),
                                  Text12(text: "입장")
                                ])),
                          )
                        ])),
              );
            }));
  }

  Widget _contant(index) {
    return Column(
      children: [
        SizedBox(height: 20),
        Text16(text: '${list[index].programTitle}을(를)', bold: true),
        Text16(text: ' 프로그램목록에서 삭제하시겠어요?', bold: true),
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
            SizedBox(
              width: 16,
            ),
            SmallButton(
              onPressed: () async {
                // 만들어야함.
                await ProgramService.fetchDeleteProgram(list[index].programId);
                getList();
                // ignore: use_build_context_synchronously
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
