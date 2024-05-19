import 'package:flutter/material.dart';
// import 'package:flutter/widgets.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/reward_notice.dart';
import 'package:forsythia/models/records/weekly_statistic_list.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/challenge/challenge_screen.dart';
import 'package:forsythia/screens/dashboard/weekly_statistics.dart';
import 'package:forsythia/screens/doghouse/move_dog.dart';
import 'package:forsythia/screens/mate/mate_screen.dart';
import 'package:forsythia/screens/watch/watch_screen.dart';
import 'package:forsythia/service/challenge_service.dart';
import 'package:forsythia/service/record_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:sliding_up_panel/sliding_up_panel.dart';

const List<IconData> icons = [
  Icons.message,
  Icons.call,
  Icons.mail,
  Icons.notifications,
  Icons.settings,
];

class DashBoardScreen extends StatefulWidget {
  const DashBoardScreen({super.key});

  @override
  State<DashBoardScreen> createState() => DashBoardScreenState();
}

class DashBoardScreenState extends State<DashBoardScreen> {
  List<Weekly> weekly = [];
  bool active = false;
  bool reward = false;
  SecureStorageService storageService = SecureStorageService();
  LoginInfo? info = LoginInfo();

  @override
  void initState() {
    super.initState();
    _getWeeklyStatisticList();
    _getRewardNotice();
  }

  // 보상있는지 없는지
  _getRewardNotice() async {
    RewardNotice rewardNotice =
        await ChallengeService.fetchRewardNotice(context);
    print(rewardNotice.data!);
    setState(() {
      if (rewardNotice.data! == true) {
        reward = true;
      } else {
        reward = false;
      }
    });
  }

  String getCurrentTimeSlot() {
    // 현재 시간을 가져옴
    DateTime now = DateTime.now();
    // 현재 시간에 따라 조건을 걸어서 반환
    if (now.hour >= 0 && now.hour < 5) {
      return "assets/images/background_4.png";
    } else if (now.hour >= 5 && now.hour < 9) {
      return "assets/images/background_1.png";
    } else if (now.hour >= 9 && now.hour < 17) {
      return "assets/images/background_2.png";
    } else if (now.hour >= 17 && now.hour < 20) {
      return "assets/images/background_3.png";
    } else {
      return "assets/images/background_4.png";
    }
  }

  // 주간기록
  _getWeeklyStatisticList() async {
    DateTime now = DateTime.now();
    WeeklyStatisticList weeklyStatisticList =
        await RecordSevice.fetchWeeklyStatisticList(
            context, now.year, now.month, now.day);
    setState(() {
      weekly = weeklyStatisticList.data!;
      active = true;
    });
    info = await storageService.getLoginInfo();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<LoginInfo?>(
      future: SecureStorageService().getLoginInfo(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else {
            var nickName = snapshot.data?.nickname ?? 'Unknown';
            return active
                ? reward
                    ? SlidingUpPanel(
                        defaultPanelState: PanelState.OPEN,
                        panel: _panelWidget(),
                        collapsed: _collapsed(),
                        body: _backgroundWidget(nickName),
                        minHeight: 50,
                        maxHeight: MediaQuery.of(context).size.height - 250,
                        borderRadius: BorderRadius.only(
                            topLeft: Radius.circular(20),
                            topRight: Radius.circular(20)),
                      )
                    : SlidingUpPanel(
                        defaultPanelState: PanelState.OPEN,
                        panel: _panelWidget(),
                        collapsed: _collapsed(),
                        body: _backgroundWidget(nickName),
                        minHeight: 50,
                        maxHeight: MediaQuery.of(context).size.height - 320,
                        borderRadius: BorderRadius.only(
                            topLeft: Radius.circular(20),
                            topRight: Radius.circular(20)),
                      )
                : Center(
                    child: CircularProgressIndicator(),
                  );
          }
        }
      },
    );
  }

  Widget _panelWidget() {
    return Container(
      padding: const EdgeInsets.only(top: 16),
      decoration: const BoxDecoration(
        color: myBackground,
        borderRadius: BorderRadius.only(
            topLeft: Radius.circular(20), topRight: Radius.circular(20)),
      ),
      child: Column(
        children: <Widget>[
          Container(
            width: 60,
            height: 6,
            decoration: BoxDecoration(
              color: myGrey,
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          const SizedBox(height: 8),
          Container(
            padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
            color: myBackground,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                reward
                    ? GestureDetector(
                        onTap: () async {
                          Reward rewards =
                              await ChallengeService.fetchReward(context);
                          print(rewards.data!.coin!);
                          print(rewards.data!.heart!);
                          setState(() {
                            reward = false;
                          });
                          SecureStorageService storageService =
                              SecureStorageService();
                          LoginInfo? info = await storageService.getLoginInfo();
                          info?.coin = (info.coin! + (rewards.data!.coin!));
                          storageService.saveLoginInfo(info!);
                          Fluttertoast.showToast(
                            msg:
                                '${rewards.data!.coin!}개의 코인과 \n ${rewards.data!.heart!}의 애정도를 얻었어요!',
                            toastLength: Toast.LENGTH_SHORT,
                            gravity: ToastGravity.CENTER,
                            backgroundColor: myYellow,
                          );
                        },
                        child: Container(
                          width: double.infinity,
                          padding: EdgeInsets.all(10),
                          margin: EdgeInsets.only(bottom: 16),
                          decoration: BoxDecoration(
                            color: myLightYellow, // 배경색
                            borderRadius:
                                BorderRadius.circular(15), // 박스의 모서리를 둥글게
                            boxShadow: [
                              BoxShadow(
                                color: Color(0xffBFC2C8)
                                    .withOpacity(0.25), // 그림자 색 (투명도 25%)
                                blurRadius: 15, // 그림자 흐림 정도
                                offset: Offset(0, 10), // 그림자의 위치 (x, y)
                              ),
                            ],
                          ),
                          child: Row(
                            children: [
                              SizedBox(width: 10),
                              Image.asset(
                                "assets/emoji/bell.png",
                                width: 30,
                                height: 30,
                              ),
                              SizedBox(width: 20),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: const [
                                  Text16(
                                    text: "아직 받지않은 보상이 있어요!",
                                    bold: true,
                                  ),
                                  SizedBox(height: 3),
                                  Text12(
                                    text: "코인과 애정도를 받으세요.",
                                    textColor: myGrey,
                                  )
                                ],
                              ),
                            ],
                          ),
                        ))
                    : SizedBox(
                        height: 0,
                      ),
                WeeklyStatisticsWidget(
                  weekly: weekly, // 주간 통계 데이터
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(
                      flex: 5,
                      child: Column(
                        children: [
                          GestureDetector(
                            onTap: () {
                              Navigator.of(context)
                                  .push(SlidePageRoute(
                                      nextPage: ChallengeScreen(
                                mission: false,
                              )))
                                  .then((result) {
                                // 돌아올 때 데이터를 수신하고 처리
                                if (result == "update") {
                                  // 리스트 업데이트 메서드 호출
                                  _getRewardNotice();
                                }
                              });
                            },
                            child: Container(
                              height: 90,
                              width: double.infinity,
                              padding: EdgeInsets.all(20),
                              margin: EdgeInsets.only(bottom: 16),
                              decoration: myYellowBoxDecoration,
                              child: Row(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: const [
                                  Text16(
                                    text: "보상",
                                    bold: true,
                                  ),
                                  Align(
                                    alignment: Alignment.bottomRight,
                                    child: Row(
                                      children: [
                                        Image(
                                          image: AssetImage(
                                              'assets/color_icons/icon_coin.png'),
                                          width: 30,
                                          height: 30,
                                          fit: BoxFit.cover,
                                          filterQuality: FilterQuality.none,
                                        ),
                                        SizedBox(width: 10),
                                        Image(
                                          image: AssetImage(
                                              'assets/color_icons/icon_love.png'),
                                          width: 30,
                                          height: 28,
                                          fit: BoxFit.cover,
                                          filterQuality: FilterQuality.none,
                                        )
                                      ],
                                    ),
                                  )
                                ],
                              ),
                            ),
                          ),
                          GestureDetector(
                            onTap: () {
                              Navigator.of(context)
                                  .push(SlidePageRoute(nextPage: MatePage()));
                            },
                            child: Container(
                              height: 90,
                              width: double.infinity,
                              padding: EdgeInsets.all(20),
                              // margin: EdgeInsets.only(bottom: 10),
                              decoration: myWhiteGreenBoxDecoration,
                              child: Row(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: const [
                                  Text16(
                                    text: "친구",
                                    bold: true,
                                  ),
                                  Align(
                                    alignment: Alignment.bottomRight,
                                    child: Row(
                                      children: [
                                        Image(
                                          image: AssetImage(
                                              'assets/color_icons/icon_mate1.png'),
                                          width: 27,
                                          height: 34,
                                          fit: BoxFit.cover,
                                          filterQuality: FilterQuality.none,
                                        ),
                                        SizedBox(width: 10),
                                        Image(
                                          image: AssetImage(
                                              'assets/color_icons/icon_mate2.png'),
                                          width: 27,
                                          height: 35,
                                          fit: BoxFit.cover,
                                          filterQuality: FilterQuality.none,
                                        )
                                      ],
                                    ),
                                  )
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                        flex: 6,
                        child: GestureDetector(
                          onTap: () {
                            Navigator.of(context)
                                .push(SlidePageRoute(nextPage: WatchScreen()));
                          },
                          child: Container(
                            height: 196,
                            padding: EdgeInsets.all(20),
                            margin: EdgeInsets.only(left: 16),
                            decoration: myBlueBoxDecoration,
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: const [
                                Text16(
                                  text: "워치",
                                  bold: true,
                                ),
                                Align(
                                  alignment: Alignment.bottomRight,
                                  child: Image(
                                    image:
                                        AssetImage('assets/images/watch.png'),
                                    width: 85,
                                    height: 120,
                                    fit: BoxFit.cover,
                                    filterQuality: FilterQuality.none,
                                  ),
                                )
                              ],
                            ),
                          ),
                        )),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _collapsed() {
    return Container(
        padding: EdgeInsets.symmetric(horizontal: 20),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20)),
          color: myBackground,
        ),
        child: Column(
          children: [
            const SizedBox(height: 16),
            Container(
              width: 60,
              height: 6,
              decoration: BoxDecoration(
                color: myGrey,
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            const SizedBox(height: 8),
            Text12(
              text: "스크롤을 올려 대시보드를 확인하세요.",
              textColor: myGrey,
            ),
          ],
        ));
  }

  Widget _backgroundWidget(String? nickName) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
            image: AssetImage(getCurrentTimeSlot()),
            fit: BoxFit.cover, // 이미지를 화면에 맞게 확장
            filterQuality: FilterQuality.none),
        borderRadius: BorderRadius.only(
            topLeft: Radius.circular(20), topRight: Radius.circular(20)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Flexible(
            flex: 1, // 첫 번째 Flexible에는 1의 비율을 할당합니다.
            child: Container(
              decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.3),
                  borderRadius: BorderRadius.circular(10.0),
                  boxShadow: [
                    BoxShadow(
                      color: Color(0xffBFC2C8).withOpacity(0.25),
                      blurRadius: 15,
                      offset: Offset(0, 10),
                    ),
                  ]),
              margin: EdgeInsets.fromLTRB(20, 60, 0, 0),
              padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
              child: Text(
                '$nickName님, 반가워요! \n오늘도 운동 시작해 볼까요?',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  height: 1.7,
                ),
              ),
            ),
          ),
          Flexible(
            flex: 3, // 두 번째 Flexible에는 3의 비율을 할당합니다. 적절한 비율을 조정해주십시오.
            child: SizedBox(
                height: MediaQuery.of(context).size.height,
                width: MediaQuery.of(context).size.width,
                child: Stack(children: [
                  ImageMove(
                      id: info!.myPetDto!.id!,
                      tier: info!.myPetDto!.tier!,
                      name: info!.myPetDto!.name!)
                ])),
          ),
        ],
      ),
    );
  }
}
