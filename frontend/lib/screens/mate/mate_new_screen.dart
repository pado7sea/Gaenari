import 'package:flutter/material.dart';
import 'package:forsythia/models/mates/accept_form.dart';
import 'package:forsythia/models/mates/mate_list.dart';
import 'package:forsythia/service/mate_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class NewMatePage extends StatefulWidget {
  const NewMatePage({super.key});

  @override
  State<NewMatePage> createState() => _NewMatePageState();
}

class _NewMatePageState extends State<NewMatePage> {
  int tapbar = 0;
  late List<Mate> list = [];
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
    _receive();
  }

  _receive() async {
    MateList response = await MateService.fetchReceivedMateList(context);
    setState(() {
      list = response.data!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
          title: "친구요청",
          back: true,
        ),
        body: Column(children: [
          Row(
            children: [
              SizedBox(
                width: 10,
              ),
              _tapbutton(0, "받은요청"),
              _tapbutton(1, "보낸요청")
            ],
          ),
          SizedBox(height: 16),
          list.isEmpty
              ? Column(
                  children: const [
                    SizedBox(height: 20),
                    Text16(text: "요청이 없습니다."),
                  ],
                )
              : tapbar == 0
                  ? _receivedMates()
                  : _sentMates(),
          SizedBox(height: 20),
        ]));
  }

  // tap부분
  Widget _tapbutton(int tapnum, taptext) {
    return GestureDetector(
        onTap: () async {
          MateList response;
          if (tapnum == 0) {
            response = await MateService.fetchReceivedMateList(context);
          } else {
            response = await MateService.fetchSentMateList(context);
          }
          setState(() {
            list = response.data!;
            tapbar = tapnum;
          });
        },
        child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Text16(
              text: taptext,
              bold: true,
              textColor: tapbar == tapnum ? myBlack : myGrey,
            )));
  }

  // 리스트부분 - 해당하는 리스트를 파라미터로 받음
  Widget _receivedMates() {
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
                        Expanded(
                          child: Row(
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
                              Expanded(
                                  child: Column(
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
                              ))
                            ],
                          ),
                        ),
                        Row(
                          children: [
                            GestureDetector(
                                onTap: () async {
                                  // 거절하는 요청
                                  AcceptForm acceptForm = AcceptForm(
                                      mateId: list[index].mateId,
                                      isAccept: false);
                                  await MateService.fetchAcceptMate(
                                      context, acceptForm);
                                  _receive();
                                },
                                child: Container(
                                    width: 70,
                                    padding: EdgeInsets.all(5),
                                    decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(10)),
                                        border: Border.all(color: myLightGrey)),
                                    child: Column(children: const [
                                      Image(
                                        image: AssetImage(
                                            "assets/icons/mate_notfriend.png"),
                                        width: 30,
                                        height: 30,
                                        fit: BoxFit.cover,
                                        filterQuality: FilterQuality.none,
                                      ),
                                      SizedBox(height: 10),
                                      Text12(text: "거절")
                                    ]))),
                            SizedBox(width: 8),
                            GestureDetector(
                                onTap: () async {
                                  // 수락하는 요청
                                  AcceptForm acceptForm = AcceptForm(
                                      mateId: list[index].mateId,
                                      isAccept: true);
                                  await MateService.fetchAcceptMate(
                                      context, acceptForm);
                                  _receive();
                                },
                                child: Container(
                                    width: 70,
                                    padding: EdgeInsets.all(5),
                                    decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(10)),
                                        border: Border.all(color: myLightGrey)),
                                    child: Column(children: const [
                                      Image(
                                        image: AssetImage(
                                            "assets/icons/mate_notfriend.png"),
                                        width: 30,
                                        height: 30,
                                        fit: BoxFit.cover,
                                        filterQuality: FilterQuality.none,
                                      ),
                                      SizedBox(height: 10),
                                      Text12(text: "수락")
                                    ]))),
                          ],
                        ),
                      ]));
            }));
  }

  Widget _sentMates() {
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
                              // 요청 기다린다는 토스트
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
                                  Text12(text: "요청중"),
                                ]))),
                      ]));
            }));
  }
}
