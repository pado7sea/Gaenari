import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:forsythia/models/challenges/mission.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/trophy.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/service/challenge_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/large_app_bar.dart';
import 'package:forsythia/widgets/none_animation_route.dart';

class ChallengePage extends StatefulWidget {
  bool mission;
  ChallengePage({super.key, required this.mission});

  @override
  State<ChallengePage> createState() => _ChallengePageState();
}

class _ChallengePageState extends State<ChallengePage> {
  late int challengeId = 0;
  // bool trophyreword = false; // 보상 받을게 있는지
  // bool missionreword = false; // 보상 받을게 있는지

  // 업적과 미션 상태를 저장할 Map 생성
  Map<String, Map<String, dynamic>> trophyStatus = {};
  Map<String, Map<String, dynamic>> missionStatus = {};

  List<int> trophyreword = [];
  List<int> missionreword = [];

  double _completeWidth = 0;
  double _notCompleteWidth = 0;

  @override
  void initState() {
    super.initState();
    challenge();
  }

  void challenge() async {
    Mission mission;
    Trophy trophy;

    mission = await ChallengeService.fetchMission();
    trophy = await ChallengeService.fetchTrophy();

    setState(() {
      if (trophy.data != null) {
        for (var item in trophy.data!) {
          if (item.obtainable != 0) {
            trophyreword.add(item.obtainable!);
          }
          if (item.type == 'D') {
            String trophyName = '${item.challengeValue}km';
            int? challengeId = item.challengeId;
            String? type = item.type;
            bool? achieved = item.isAchieved;
            int? obtainable = item.obtainable;
            int? challengeValue = item.challengeValue?.toInt();
            int? memberValue = item.memberValue?.toInt();
            int? coin = item.coin?.toInt();
            trophyStatus[trophyName] = {
              'challengeId': challengeId!,
              'type': type!,
              'achieved': achieved!,
              'obtainable': obtainable!,
              'challengeValue': challengeValue!,
              'memberValue': memberValue!,
              'coin': coin!
            };
          } else if (item.type == 'T') {
            String trophyName = '${item.challengeValue! ~/ 3600}시간';
            int? challengeId = item.challengeId;
            String? type = item.type;
            bool? achieved = item.isAchieved;
            int? obtainable = item.obtainable;
            int? challengeValue = item.challengeValue?.toInt();
            int? memberValue = item.memberValue?.toInt();
            int? coin = item.coin?.toInt();
            trophyStatus[trophyName] = {
              'challengeId': challengeId!,
              'type': type!,
              'achieved': achieved!,
              'obtainable': obtainable!,
              'challengeValue': (challengeValue! / 3600).toInt(),
              'memberValue': (memberValue! / 3600).toInt(),
              'coin': coin!
            };
          }
        }
      }

      if (mission.data != null) {
        for (var item in mission.data!) {
          if (item.obtainable != 0) {
            missionreword.add(item.obtainable!);
          }
          if (item.type == 'D') {
            String missionName = '${item.challengeValue}KM';
            int? challengeId = item.challengeId;
            String? type = item.type;
            int? coin = item.coin;
            int? heart = item.heart;
            int? count = item.count;
            int? obtainable = item.obtainable;
            missionStatus[missionName] = {
              'challengeId': challengeId!,
              'type': type!,
              'coin': coin!,
              'heart': heart!,
              'count': count!,
              'obtainable': obtainable!
            };
          } else if (item.type == 'T') {
            String missionName = '${item.challengeValue! ~/ 60}분';
            int? challengeId = item.challengeId;
            String? type = item.type;
            int? coin = item.coin;
            int? heart = item.heart;
            int? count = item.count;
            int? obtainable = item.obtainable;
            missionStatus[missionName] = {
              'challengeId': challengeId!,
              'type': type!,
              'coin': coin!,
              'heart': heart!,
              'count': count!,
              'obtainable': obtainable!
            };
          }
        }
      }
    });
    print('챌린지챌린지');
    print('Mission Status: $missionStatus');
    print(trophyreword);
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
            widget.mission
                ? Wrap(
                    children: [
                      _mission(),
                    ],
                  )
                : Center(
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(0, 20, 0, 10),
                      child: Column(
                        children: [_complete(), _notcomplete(), _clear()],
                      ),
                    ),
                  ),
            SizedBox(height: 20)
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
                widget.mission = false;
              });
              challenge();
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
                  textColor: widget.mission ? myGrey : myBlack,
                ),
                Column(
                  children: [
                    trophyreword.isNotEmpty
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
                widget.mission = true;
              });
              challenge();
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
                  textColor: widget.mission ? myBlack : myGrey,
                ),
                Column(
                  children: [
                    missionreword.isNotEmpty
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
      late Widget missionImage;

      // value['type']에 따라 다른 이미지 선택
      if (value['type'] == 'D') {
        missionImage = Image(
          image: AssetImage('assets/emoji/running.png'),
          width: 40,
          height: 40,
          fit: BoxFit.cover,
        );
      } else if (value['type'] == 'T') {
        missionImage = Image(
          image: AssetImage('assets/emoji/clock.png'),
          width: 40,
          height: 40,
          fit: BoxFit.cover,
        );
      }

      if (value['achieved'] == true && value['obtainable'] != 0) {
        completedTrophiesWidgets.add(
          Padding(
            padding: const EdgeInsets.only(bottom: 10),
            child: LayoutBuilder(
                builder: (BuildContext context, BoxConstraints constraints) {
              _completeWidth = constraints.maxWidth;
              return Container(
                decoration: myYellowBorderBoxDecoration,
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
                                missionImage,
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
                                    barwidth: _completeWidth),
                              ],
                            ),
                          ],
                        ),
                      ),
                      SizedBox(width: 10),
                      Flexible(
                          flex: 3,
                          child: Button(
                            clear: true,
                            challengeId: value['challengeId'],
                            onChallenge: () {
                              challenge();
                            },
                            challengeValue: value['challengeValue'],
                            memberValue: value['memberValue'],
                            type: value['type'],
                            coin: value['coin'],
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
      late Widget missionImage;

      // value['type']에 따라 다른 이미지 선택
      if (value['type'] == 'D') {
        missionImage = Image(
          image: AssetImage('assets/emoji/running.png'),
          width: 40,
          height: 40,
          fit: BoxFit.cover,
        );
      } else if (value['type'] == 'T') {
        missionImage = Image(
          image: AssetImage('assets/emoji/clock.png'),
          width: 40,
          height: 40,
          fit: BoxFit.cover,
        );
      }

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
                          missionImage,
                          SizedBox(width: 10),
                          Text16(text: '누적 $key 달성', bold: true),
                        ]),
                        Button(
                          clear: false,
                          challengeId: value['challengeId'],
                          onChallenge: () {
                            challenge();
                          },
                          challengeValue: value['challengeValue'],
                          memberValue: value['memberValue'],
                          type: value['type'],
                          coin: value['obtainable'],
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
              color: myYellow,
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

  Widget _mission() {
    List<Widget> MissionWidgets = [];
    List<Widget> completedMissionWidgets = [];

    missionStatus.forEach((key, value) {
      // reword
      if (value['obtainable'] != 0) {
        completedMissionWidgets.add(Container());
      }
      // 이미지에 대한 변수 선언
      late Widget missionImage;

      // value['type']에 따라 다른 이미지 선택
      if (value['type'] == 'D') {
        missionImage = Image(
          image: AssetImage('assets/emoji/running.png'),
          width: 60,
          height: 60,
          fit: BoxFit.cover,
        );
      } else if (value['type'] == 'T') {
        missionImage = Image(
          image: AssetImage('assets/emoji/clock.png'),
          width: 60,
          height: 60,
          fit: BoxFit.cover,
        );
      }

      MissionWidgets.add(Padding(
        padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
        child: Container(
          decoration: myBorderBoxDecoration,
          child: Padding(
            padding: const EdgeInsets.fromLTRB(10, 20, 10, 10),
            child: Column(
              children: [
                Text16(text: key, bold: true),
                SizedBox(height: 10),
                // value['type']에 따라 다른 이미지 표시
                missionImage,
                SizedBox(height: 10),
                Expanded(
                  child: MissionBtn(
                    obtainable: value['obtainable'],
                    count: value['count'],
                    challengeId: value['challengeId'],
                    onChallenge: () {
                      challenge();
                    },
                  ),
                ),
              ],
            ),
          ),
        ),
      ));
    });

    return Padding(
        padding: const EdgeInsets.symmetric(horizontal: 20),
        child: GridView.builder(
            shrinkWrap: true,
            physics: NeverScrollableScrollPhysics(),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2, // 한 줄에 두 개의 아이템을 표시
              crossAxisSpacing: 0.0, // 아이템 간의 가로 간격
              mainAxisSpacing: 0.0, // 아이템 간의 세로 간격
              childAspectRatio: 0.95, // 각 아이템의 가로 길이와 세로 길이의 비율
            ),
            itemCount: missionStatus.length,
            itemBuilder: (BuildContext context, int index) {
              return MissionWidgets[index];
            }));
  }
}

class Progressbar extends StatelessWidget {
  final bool clear;
  final int memberValue;
  final int challengeValue;
  final double barwidth;

  const Progressbar({
    Key? key,
    required this.clear,
    required this.memberValue,
    required this.challengeValue,
    required this.barwidth,
  }) : super(key: key);

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

class Button extends StatefulWidget {
  final bool clear;
  final int challengeId;
  final int memberValue;
  final int challengeValue;
  final String type;
  final int coin;
  final VoidCallback onChallenge;

  const Button({
    super.key,
    required this.clear,
    required this.challengeId,
    required this.memberValue,
    required this.challengeValue,
    required this.type,
    required this.coin,
    required this.onChallenge,
  });

  @override
  State<Button> createState() => _ButtonState();
}

class _ButtonState extends State<Button> {
  @override
  void initState() {
    super.initState();
    widget.onChallenge();
  }

  @override
  Widget build(BuildContext context) {
    return widget.clear
        ? ElevatedButton(
            onPressed: () async {
              Reward reward = await ChallengeService.fetchRewardChallenge(
                  widget.challengeId);
              print(reward.data!.coin!);
              print(reward.data!.heart!);
              SecureStorageService storageService = SecureStorageService();
              LoginInfo? info = await storageService.getLoginInfo();
              info?.coin = (info.coin! + (reward.data!.coin!));
              storageService.saveLoginInfo(info!);
              Fluttertoast.showToast(
                msg:
                    '${reward.data!.coin!}개의 코인과 \n ${reward.data!.heart!}의 애정도를 얻었어요!',
                toastLength: Toast.LENGTH_SHORT,
                gravity: ToastGravity.CENTER,
                backgroundColor: myYellow,
              );
              Navigator.pop(context);
              Navigator.push(
                  context,
                  NonePageRoute(
                      nextPage: ChallengePage(
                    mission: false,
                  )));
            },
            style: ElevatedButton.styleFrom(
                padding: EdgeInsets.zero,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                backgroundColor: myYellow),
            child: Container(
              padding: const EdgeInsets.all(6.0),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Text(
                        '${widget.coin} ',
                        style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: myBlack),
                      ),
                      Image(
                        image: AssetImage('assets/color_icons/icon_coin.png'),
                        width: 15,
                        height: 15,
                        fit: BoxFit.cover,
                        filterQuality: FilterQuality.high,
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
                    text: widget.memberValue.toString() +
                        (widget.type == 'D' ? 'km/' : '시간/'),
                    textColor: myGrey,
                    bold: true),
                Text12(
                    text: widget.challengeValue.toString() +
                        (widget.type == 'D' ? 'km' : '시간'),
                    textColor: myGrey,
                    bold: true),
                SizedBox(width: 10)
              ],
            ));
  }
}

class MissionBtn extends StatefulWidget {
  final int obtainable;
  final int count;
  final int challengeId;
  final VoidCallback onChallenge;

  const MissionBtn({
    super.key,
    required this.obtainable,
    required this.count,
    required this.challengeId,
    required this.onChallenge,
  });

  @override
  State<MissionBtn> createState() => _MissionBtnState();
}

class _MissionBtnState extends State<MissionBtn> {
  bool active = false;
  @override
  void initState() {
    super.initState();
    widget.onChallenge();
    if (widget.obtainable != 0) {
      active = true;
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: () async {
          if (widget.obtainable != 0) {
            setState(() {
              active = false;
            });
            Reward reward =
                await ChallengeService.fetchRewardChallenge(widget.challengeId);
            print(reward.data!.coin!);
            print(reward.data!.heart!);
            SecureStorageService storageService = SecureStorageService();
            LoginInfo? info = await storageService.getLoginInfo();
            info?.coin = (info.coin! + (reward.data!.coin!));
            storageService.saveLoginInfo(info!);
            Fluttertoast.showToast(
              msg:
                  '${reward.data!.coin!}개의 코인과 \n ${reward.data!.heart!}의 애정도를 얻었어요!',
              toastLength: Toast.LENGTH_SHORT,
              gravity: ToastGravity.CENTER,
              backgroundColor: myYellow,
            );

            Navigator.pop(context);
            Navigator.push(
                context,
                NonePageRoute(
                    nextPage: ChallengePage(
                  mission: true,
                )));
          }
        },
        child: Container(
            decoration: BoxDecoration(
                color: active ? myLightGreen : myLightGrey,
                borderRadius: BorderRadius.circular(10)),
            child: Center(
              child: Text16(
                text: active ? '보상받기' : '${widget.count} 회',
                textColor: Colors.white,
              ),
            )));
  }
}
