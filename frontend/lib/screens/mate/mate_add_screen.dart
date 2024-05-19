import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:forsythia/models/mates/search_mate_list.dart';
import 'package:forsythia/screens/mate/search.dart';
import 'package:forsythia/service/mate_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:fluttertoast/fluttertoast.dart';

class AddMatePage extends StatefulWidget {
  const AddMatePage({super.key});

  @override
  State<AddMatePage> createState() => _AddMatePageState();
}

class _AddMatePageState extends State<AddMatePage> {
  late String searchedText = '';
  late List<SearchMate> list = [];
  bool search = false;
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
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
          title: "친구검색",
          back: true,
        ),
        body: Column(children: [
          SizedBox(height: 20), // 검색창과 텍스트 사이의 간격
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 20), // 검색창 좌우 마진
            child: MateSearchBar(
              onSearch: (text) async {
                if (text != "") {
                  SearchMateList response =
                      await MateService.fetchSearchMateList(context, text);
                  setState(() {
                    search = true;
                    list = response.data!;
                    searchedText = text;
                  });
                }
              },
            ),
          ),
          SizedBox(height: 16),
          list.isEmpty && search ? Text("검색결과 없음") : _mates(),
          SizedBox(height: 20),
        ]));
  }

  _mateAdd(index) async {
    await MateService.fetchAddMate(context, list[index].memberId);
    Fluttertoast.showToast(
      msg: '${list[index].nickName}님에게 친구요청을 보냈어요!',
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.BOTTOM,
      backgroundColor: myMainGreen,
    );
    setState(() {
      list[index].state = "WAITTING";
    });
  }

  _alreadyMate(index) {
    Fluttertoast.showToast(
      msg: list[index].state == "WAITTING"
          ? '수락을 기다리고 있어요.'
          : '${list[index].nickName}님과 친구예요.',
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.BOTTOM,
      backgroundColor: myMainGreen,
    );
  }

  Widget _mates() {
    return Expanded(
        child: ListView.builder(
            shrinkWrap: true, // 필요한 만큼의 공간만 차지하도록 설정
            itemCount: list.length,
            itemBuilder: (BuildContext context, int index) {
              return Container(
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
                                image:
                                    AssetImage(images[list[index].petId! - 1]),
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
                              list[index].state == "NOTFRIEND"
                                  ? showModalBottomSheet(
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
                                    )
                                  : _alreadyMate(index);
                            },
                            child: Container(
                                width: 70,
                                padding: EdgeInsets.all(5),
                                decoration: BoxDecoration(
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(10)),
                                    border: Border.all(color: myLightGrey)),
                                child: Column(children: [
                                  Image(
                                    image: AssetImage(list[index].state ==
                                            "NOTFRIEND"
                                        ? "assets/icons/mate_notfriend.png"
                                        : list[index].state == "FRIEND"
                                            ? "assets/icons/mate_friend.png"
                                            : "assets/icons/mate_waitfriend.png"),
                                    width: 30,
                                    height: 30,
                                    fit: BoxFit.cover,
                                    filterQuality: FilterQuality.none,
                                  ),
                                  SizedBox(height: 10),
                                  Text12(
                                      text: list[index].state == "NOTFRIEND"
                                          ? "친구추가"
                                          : list[index].state == "FRIEND"
                                              ? "친구"
                                              : "요청중")
                                ])))
                      ]));
            }));
  }

  Widget _contant(index) {
    return Column(
      children: [
        SizedBox(height: 20),
        Text16(text: '${list[index].nickName}님에게', bold: true),
        Text16(text: ' 친구신청을 보낼까요?', bold: true),
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
              onPressed: () {
                _mateAdd(index);
                Navigator.of(context).pop();
              },
              text: "신청",
              active: true,
              widthPadding: 50,
            ),
          ],
        ),
        SizedBox(height: 10),
        Text12(text: "친구신청 후 요청취소가 불가합니다.", textColor: myGrey),
      ],
    );
  }
}
