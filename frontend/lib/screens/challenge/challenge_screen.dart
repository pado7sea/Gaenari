import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:forsythia/models/challenges/mission.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/trophy.dart';
import 'package:forsythia/service/challenge_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/large_app_bar.dart';

class ChallengePage extends StatefulWidget {
  const ChallengePage({super.key});

  @override
  State<ChallengePage> createState() => _ChallengePageState();
}

class _ChallengePageState extends State<ChallengePage> {
  late int challengeId = 0;
  bool mission = false; // 미션 or 업적
  bool trophyreword = true; // 보상 받을게 있는지
  bool missionreword = true; // 보상 받을게 있는지

  // 업적과 미션 상태를 저장할 Map 생성
  Map<String, Map<String, dynamic>> trophyStatus = {};
  Map<String, bool> missionStatus = {};

  double _completeWidth = 0;
  double _notCompleteWidth = 0;

  @override
  void initState() {
    super.initState();
    challenge();
    reward(challengeId);
  }

  void challenge() async {
    Mission mission;
    Trophy trophy;

    mission = await ChallengeService.fetchMission();
    trophy = await ChallengeService.fetchTrophy();

    setState(() {
      if (trophy.data != null) {
        for (var item in trophy.data!) {
          if (item.type == 'D') {
            String trophyName = '${item.challengeValue}km';
            int? challengeId = item.challengeId;
            String? type = item.type;
            bool? achieved = item.isAchieved;
            int? obtainable = item.obtainable;
            int? challengeValue = item.challengeValue?.toInt();
            int? memberValue = item.memberValue?.toInt();
            trophyStatus[trophyName] = {
              'challengeId': challengeId!,
              'type': type!,
              'achieved': achieved!,
              'obtainable': obtainable!,
              'challengeValue': challengeValue!,
              'memberValue': memberValue!,
            };
          } else if (item.type == 'T') {
            String trophyName = '${item.challengeValue! ~/ 3600}시간';
            int? challengeId = item.challengeId;
            String? type = item.type;
            bool? achieved = item.isAchieved;
            int? obtainable = item.obtainable;
            int? challengeValue = item.challengeValue?.toInt();
            int? memberValue = item.memberValue?.toInt();
            trophyStatus[trophyName] = {
              'challengeId': challengeId!,
              'type': type!,
              'achieved': achieved!,
              'obtainable': obtainable!,
              'challengeValue': (challengeValue! / 3600).toInt(),
              'memberValue': (memberValue! / 3600).toInt(),
            };
          }
        }
      }
    });
    print('챌린지챌린지');
    print(trophy);
    print('Trophy Status: $trophyStatus');
    print('Mission Status: $missionStatus');
  }

  void reward(int challengeId) async {
    Reward reward;
    reward = await ChallengeService.fetchRewardChallenge(challengeId);
    setState(() {});
    print('리워드');
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(
        title: "도전과제",
        sentence: "도전과제를 달성하고 \n많은 보상과 강아지를 레벨업",
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            _togglebutton(),
            mission
                ? Wrap(
                    children: [
                      _missioncomplete(),
                    ],
                  )
                : Center(
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(0, 20, 0, 10),
                      child: Column(
                        children: [_complete(), _notcomplete(), _clear()],
                      ),
                    ),
                  )
          ],
        ),
      ),
    );
  }

  Widget _togglebutton() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
      child: Row(
        children: [
          GestureDetector(
            onTap: () {
              setState(() {
                mission = false;
              });
            },
            child: Row(
              children: [
                Image(
                  image: AssetImage('assets/emoji/trophy.png'),
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
                SizedBox(width: 10),
                Text20(
                  text: '업적',
                  bold: true,
                  textColor: mission ? myGrey : myBlack,
                ),
                Column(
                  children: [
                    trophyreword
                        ? Padding(
                            padding: const EdgeInsets.fromLTRB(8, 0, 0, 0),
                            child: Container(
                              width: 8,
                              height: 8,
                              decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                color: myRed,
                              ),
                            ),
                          )
                        : Container(),
                    SizedBox(height: 20)
                  ],
                )
              ],
            ),
          ),
          SizedBox(width: 10),
          Container(
            width: 2,
            height: 20,
            decoration: BoxDecoration(color: myGrey),
          ),
          SizedBox(width: 10),
          GestureDetector(
            onTap: () {
              setState(() {
                mission = true;
              });
            },
            child: Row(
              children: [
                Image(
                  image: AssetImage('assets/emoji/v.png'),
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
                SizedBox(width: 10),
                Text20(
                  text: '미션',
                  bold: true,
                  textColor: mission ? myBlack : myGrey,
                ),
                Column(
                  children: [
                    missionreword
                        ? Padding(
                            padding: const EdgeInsets.fromLTRB(8, 0, 0, 0),
                            child: Container(
                              width: 8,
                              height: 8,
                              decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                color: myRed,
                              ),
                            ),
                          )
                        : Container(),
                    SizedBox(height: 20)
                  ],
                )
              ],
            ),
          ),
        ],
      ),
    );
  }

  // 달성한거
  Widget _complete() {
    List<Widget> completedTrophiesWidgets = [];
    trophyStatus.forEach((key, value) {
      if (value['achieved'] == true && value['obtainable'] != 0) {
        completedTrophiesWidgets.add(
          Padding(
            padding: const EdgeInsets.only(bottom: 20),
            child: LayoutBuilder(
                builder: (BuildContext context, BoxConstraints constraints) {
              _completeWidth = constraints.maxWidth;
              return Container(
                decoration: myBorderBoxDecoration,
                child: Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Row(
                    children: [
                      Flexible(
                        flex: 7,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Image(
                                  image: AssetImage('assets/emoji/running.png'),
                                  width: 40,
                                  height: 40,
                                  fit: BoxFit.cover,
                                ),
                                SizedBox(width: 10),
                                Text16(text: '누적 $key 달성', bold: true),
                              ],
                            ),
                            SizedBox(height: 10),
                            Stack(
                              children: [
                                _bar(),
                                Progressbar(
                                  clear: false,
                                  memberValue: value['memberValue'],
                                  challengeValue: value['challengeValue'],
                                  barwidth: _completeWidth,
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                      SizedBox(width: 10),
                      Flexible(
                          flex: 4,
                          child: Button(
                            clear: true,
                            challengeValue: value['challengeValue'],
                            memberValue: value['memberValue'],
                            type: value['type'],
                            onPressed: () {
                              reward(value['challengeId']);
                              challenge();
                            },
                          )),
                    ],
                  ),
                ),
              );
            }),
          ),
        );
      }
    });

    // completedTrophiesWidgets가 비어있으면 trophyreward를 false로 설정
    if (completedTrophiesWidgets.isEmpty) {
      trophyreword = false;
    } else {
      trophyreword = true;
    }

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: completedTrophiesWidgets.length,
        itemBuilder: (BuildContext context, int index) {
          return completedTrophiesWidgets[index];
        },
      ),
    );
  }

  // 달성 못한거
  Widget _notcomplete() {
    List<Widget> notcompletedTrophiesWidgets = [];
    trophyStatus.forEach((key, value) {
      if (value['achieved'] == false) {
        notcompletedTrophiesWidgets.add(Padding(
          padding: const EdgeInsets.fromLTRB(10, 0, 10, 10),
          child: LayoutBuilder(
              builder: (BuildContext context, BoxConstraints constraints) {
            _notCompleteWidth = constraints.maxWidth;
            return Container(
              width: MediaQuery.of(context).size.width - 60,
              decoration: myBorderBoxDecoration,
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Row(children: [
                          Image(
                            image: AssetImage('assets/emoji/running.png'),
                            width: 40,
                            height: 40,
                            fit: BoxFit.cover,
                          ),
                          SizedBox(width: 10),
                          Text16(text: '누적 $key 달성', bold: true),
                        ]),
                        Button(
                          clear: false,
                          challengeValue: value['challengeValue'],
                          memberValue: value['memberValue'],
                          type: value['type'],
                          onPressed: () {
                            reward(value['challengeId']);
                            challenge();
                          },
                        )
                      ],
                    ),
                    SizedBox(height: 10),
                    Stack(
                      children: [
                        _bar(),
                        Progressbar(
                            clear: true,
                            memberValue: value['memberValue'],
                            challengeValue: value['challengeValue'],
                            barwidth: _notCompleteWidth)
                      ],
                    ),
                  ],
                ),
              ),
            );
          }),
        ));
      }
    });

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: notcompletedTrophiesWidgets.length,
        itemBuilder: (BuildContext context, int index) {
          return notcompletedTrophiesWidgets[index];
        },
      ),
    );
  }

  // 보상도 다 받은
  Widget _clear() {
    List<Widget> clearTrophiesWidgets = [];
    trophyStatus.forEach((key, value) {
      if (value['achieved'] == true && value['obtainable'] == 0) {
        clearTrophiesWidgets.add(Padding(
          padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
          child: Container(
            decoration: BoxDecoration(
              color: myMainGreen,
              borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
              boxShadow: [
                BoxShadow(
                  color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
                  blurRadius: 15, // 그림자 흐림 정도
                  offset: Offset(0, 10), // 그림자의 위치 (x, y)
                ),
              ],
            ),
            width: MediaQuery.of(context).size.width - 60,
            height: 70,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image(
                  image: AssetImage('assets/emoji/party.png'),
                  width: 20,
                  height: 20,
                  fit: BoxFit.cover,
                ),
                Text16(
                  text: ' 누적 $key 달리기 달성 완료 ',
                  bold: true,
                ),
                Image(
                  image: AssetImage('assets/emoji/party.png'),
                  width: 20,
                  height: 20,
                  fit: BoxFit.cover,
                )
              ],
            ),
          ),
        ));
      }
    });

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: clearTrophiesWidgets.length,
        itemBuilder: (BuildContext context, int index) {
          return clearTrophiesWidgets[index];
        },
      ),
    );
  }

  Widget _bar() {
    return Container(
      height: 6,
      decoration:
          BoxDecoration(borderRadius: BorderRadius.circular(3), color: myGrey),
    );
  }

  // 미션 --------------------------------

  Widget _missioncomplete() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
      child: Container(
        decoration: myBorderBoxDecoration,
        child: Padding(
          padding: const EdgeInsets.fromLTRB(10, 20, 10, 10),
          child: Column(
            children: [
              Text16(text: '1KM', bold: true),
              SizedBox(height: 10),
              Image(
                image: AssetImage('assets/emoji/running.png'),
                width: 60,
                height: 60,
                fit: BoxFit.cover,
              ),
              SizedBox(height: 10),
              ElevatedButton(
                onPressed: () {
                  setState(() {
                    missionreword = false;
                  });
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: missionreword ? myLightGreen : myLightGrey,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20),
                  child: Text16(
                    text: '보상받기',
                    textColor: Colors.white,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class Progressbar extends StatelessWidget {
  final bool clear;
  final int memberValue;
  final int challengeValue;
  final double barwidth;

  const Progressbar(
      {Key? key,
      required this.clear,
      required this.memberValue,
      required this.challengeValue,
      required this.barwidth})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 6,
      width: barwidth * (memberValue / challengeValue),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(3),
        color: clear ? myLightGreen : myYellow,
      ),
    );
  }
}

class Button extends StatelessWidget {
  final bool clear;
  final int memberValue;
  final int challengeValue;
  final String type;
  final VoidCallback onPressed;

  const Button({
    super.key,
    required this.clear,
    required this.memberValue,
    required this.challengeValue,
    required this.type,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return clear
        ? ElevatedButton(
            onPressed: onPressed,
            style: ElevatedButton.styleFrom(
                padding: EdgeInsets.zero,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                backgroundColor: myLightGreen),
            child: Container(
              padding: const EdgeInsets.all(6.0),
              child: Column(
                children: const [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Text(
                        '400 ',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: myBlack),
                      ),
                      Image(
                        image: AssetImage('assets/emoji/money.png'),
                        width: 18,
                        height: 18,
                        fit: BoxFit.cover,
                      ),
                      SizedBox(width: 10),
                      Text(
                        '4 ',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: myBlack),
                      ),
                      Image(
                        image: AssetImage('assets/emoji/heart.png'),
                        width: 18,
                        height: 18,
                        fit: BoxFit.cover,
                      ),
                    ],
                  ),
                  SizedBox(height: 3),
                  Text(
                    '보상받기',
                    style: TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.bold,
                        color: myBlack),
                  )
                ],
              ),
            ),
          )
        : Container(
            alignment: Alignment.centerRight,
            child: Row(
              children: [
                Text16(
                    text:
                        memberValue.toString() + (type == 'D' ? 'km/' : '시간/'),
                    textColor: myGrey,
                    bold: true),
                Text12(
                    text:
                        challengeValue.toString() + (type == 'D' ? 'km' : '시간'),
                    textColor: myGrey,
                    bold: true),
                SizedBox(width: 10)
              ],
            ));
  }
}
