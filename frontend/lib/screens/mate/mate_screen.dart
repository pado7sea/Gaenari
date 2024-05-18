import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:forsythia/models/mates/mate_list.dart';
import 'package:forsythia/screens/mate/mate_add_screen.dart';
import 'package:forsythia/screens/mate/mate_doghouse_screen.dart';
import 'package:forsythia/screens/mate/mate_new_screen.dart';
import 'package:forsythia/service/mate_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/large_app_bar.dart';
import 'package:flutter_slidable/flutter_slidable.dart';

class MatePage extends StatefulWidget {
  const MatePage({super.key});

  @override
  State<MatePage> createState() => _MatePageState();
}

class ListItem {
  final String title;
  ListItem(this.title);
}

class _MatePageState extends State<MatePage> {
  late List<Mate> list = [];
  late SlidableController slidableController =
      SlidableController(); // 슬라이드 컨트롤러 추가

  List<String> images = [
    'assets/gif/shepherd_standandlook.gif',
    'assets/gif/grayhound_standandlook.gif',
    'assets/gif/husky_standandlook.gif',
    'assets/gif/pomeranian1_standandlook.gif',
    'assets/gif/pomeranian2_standandlook.gif',
    'assets/gif/shiba_standandlook.gif',
    'assets/gif/pug_standandlook.gif',
    'assets/gif/retriever1_standandlook.gif',
    'assets/gif/retriever2_standandlook.gif',
    'assets/gif/wolf_standandlook.gif',
  ];

  @override
  void initState() {
    super.initState();
    getList();
  }

  getList() async {
    MateList response = await MateService.fetchMateList(context);
    setState(() {
      list = response.data!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(
        title: "친구",
        sentence: "당신의 친구 목록이 보이는 공간입니다. \n친구집에 놀러가보세요",
      ),
      body: Column(
        children: [
          _topButtons(),
          SizedBox(height: 16),
          _mates(),
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
                .push(SlidePageRoute(nextPage: NewMatePage()))
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
        GestureDetector(
          onTap: () {
            Navigator.of(context).push(SlidePageRoute(nextPage: AddMatePage()));
          },
          child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Text16(
                  text: "검색 ",
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
        SizedBox(width: 8),
      ],
    );
  }

  Widget _mates() {
    return Expanded(
        child: ListView.builder(
            shrinkWrap: true, // 필요한 만큼의 공간만 차지하도록 설정
            itemCount: list.length,
            itemBuilder: (BuildContext context, int index) {
              return Slidable(
                controller: slidableController, // 슬라이드 컨트롤러 추가
                key: Key(list[index].mateId.toString()),
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
                                      images[list[index].petId! - 1]),
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
                                    text: list[index].nickName!,
                                    bold: true,
                                  ),
                                  SizedBox(height: 10),
                                  Row(
                                    children: [
                                      Image.asset(
                                        "assets/dog_tier/tier_${list[index].petTier!}.png",
                                        width: 18,
                                        height: 20,
                                        fit: BoxFit.cover,
                                        filterQuality: FilterQuality.none,
                                      ),
                                      SizedBox(width: 5),
                                      Text12(text: ' ${list[index].petName!}'),
                                    ],
                                  ),
                                ],
                              )
                            ],
                          ),
                          GestureDetector(
                            onTap: () {
                              Navigator.of(context).push(SlidePageRoute(
                                  nextPage: MateDogHouseScreen(
                                memberId: list[index].memberId!,
                                memberName: list[index].nickName!,
                              )));
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
        Text16(text: '${list[index].nickName}님을', bold: true),
        Text16(text: ' 친구목록에서 삭제하시겠어요?', bold: true),
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
                await MateService.fetchDeleteMate(
                    context, list[index].memberId);
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
