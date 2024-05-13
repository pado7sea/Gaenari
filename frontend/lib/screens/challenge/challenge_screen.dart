import 'package:flutter/material.dart';
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
  // int? CompleteRunIndex = null;
  // int? CompleteTimeIndex = null;

  bool mission = false; // 미션 or 업적
  bool clear = false; // 달성했는지
  bool trophyreword = true; // 보상 받을게 있는지
  bool missionreword = true; // 보상 받을게 있는지

  List<String> run = [
    '1km',
    '4km',
    '16km',
    '64km',
    '256km',
    '1024km',
  ];

  List<String> time = [
    '1시간',
    '4시간',
    '16시간',
    '64시간',
    '256시간',
    '1024시간',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(
        title: "도전과제",
        sentence: "도전과제를 달성하고 \n많은 보상과 강아지를 레벨업",
      ),
      body: Column(
        children: [
          _togglebutton(),
          mission
              ? SingleChildScrollView(
                  child: Center(
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(0, 20, 0, 10),
                    child: Column(
                      children: [
                        _complete(),
                        _notcomplete(),
                        _notcomplete(),
                        _notcomplete(),
                        _clear()
                      ],
                    ),
                  ),
                ))
              : Wrap(
                  children: [
                    _missioncomplete(),
                    _missioncomplete(),
                    _missioncomplete(),
                  ],
                )
        ],
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
                mission = true;
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
                  textColor: mission ? myBlack : myGrey,
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
                mission = false;
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
                  textColor: mission ? myGrey : myBlack,
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
    setState(() {
      clear = true;
    });
    return Container(
      width: MediaQuery.of(context).size.width - 40,
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
                    children: const [
                      Image(
                        image: AssetImage('assets/emoji/running.png'),
                        width: 40,
                        height: 40,
                        fit: BoxFit.cover,
                      ),
                      SizedBox(width: 10),
                      Text16(text: '4km 달리기', bold: true),
                    ],
                  ),
                  SizedBox(height: 10),
                  Stack(
                    children: [_bar(), _progressbar()],
                  ),
                ],
              ),
            ),
            SizedBox(width: 10),
            Flexible(flex: 4, child: _button()),
          ],
        ),
      ),
    );
  }

  // 달성 못한거
  Widget _notcomplete() {
    setState(() {
      clear = false;
    });
    return Padding(
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 0),
      child: Container(
        width: MediaQuery.of(context).size.width - 60,
        decoration: myBorderBoxDecoration,
        child: Padding(
          padding: const EdgeInsets.all(10.0),
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Row(children: const [
                    Image(
                      image: AssetImage('assets/emoji/running.png'),
                      width: 40,
                      height: 40,
                      fit: BoxFit.cover,
                    ),
                    SizedBox(width: 10),
                    Text16(text: '4km 달리기', bold: true),
                  ]),
                  _button(),
                ],
              ),
              SizedBox(height: 10),
              Stack(
                children: [_bar(), _progressbar()],
              ),
            ],
          ),
        ),
      ),
    );
  }

  // 보상도 다 받은
  Widget _clear() {
    return Padding(
      padding: const EdgeInsets.all(10.0),
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
          children: const [
            Image(
              image: AssetImage('assets/emoji/party.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(
              text: ' 1Km 달리기 달성 완료 ',
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
    );
  }

  Widget _bar() {
    return Container(
      height: 6,
      decoration:
          BoxDecoration(borderRadius: BorderRadius.circular(3), color: myGrey),
    );
  }

  Widget _progressbar() {
    return clear
        ? Container(
            height: 6,
            decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(3), color: myLightGreen),
          )
        : Container(
            width: 100,
            height: 6,
            decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(3), color: myYellow),
          );
  }

  Widget _button() {
    return clear
        ? ElevatedButton(
            onPressed: () {
              // 버튼을 클릭했을 때 실행되는 동작
            },
            style: ElevatedButton.styleFrom(
                padding: EdgeInsets.zero,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
                backgroundColor: myMainGreen),
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
              children: const [
                Text16(text: '3.2Km', textColor: myGrey, bold: true),
                Text12(text: '/5Km', textColor: myGrey, bold: true),
                SizedBox(width: 10)
              ],
            ));
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
