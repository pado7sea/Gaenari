import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/reward_notice.dart';
import 'package:forsythia/models/records/weekly_statistic_list.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/challenge/challenge_screen.dart';
import 'package:forsythia/screens/dashboard/weekly_statistics.dart';
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

  @override
  void initState() {
    super.initState();
    _getWeeklyStatisticList();
    _getRewardNotice();
  }

  // 보상있는지 없는지
  _getRewardNotice() async {
    RewardNotice rewardNotice = await ChallengeService.fetchRewardNotice();
    print(rewardNotice.data!);
    setState(() {
      if (rewardNotice.data! == true) {
        reward = true;
      } else {
        reward = false;
      }
    });
  }

  // 주간기록
  _getWeeklyStatisticList() async {
    DateTime now = DateTime.now();
    WeeklyStatisticList weeklyStatisticList =
        await RecordSevice.fetchWeeklyStatisticList(
            now.year, now.month, now.day);
    setState(() {
      weekly = weeklyStatisticList.data!;
      active = true;
    });
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
                    ?
                    // ? Container(
                    //     width: double.infinity,
                    //     padding: EdgeInsets.all(10),
                    //     margin: EdgeInsets.only(bottom: 16),
                    //     decoration: myBoxDecoration,
                    //     child: Row(
                    //       mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    //       children: [
                    // Row(
                    //   children: [
                    //     Image.asset(
                    //       "assets/emoji/bell.png",
                    //       width: 30,
                    //       height: 30,
                    //     ),
                    //     SizedBox(width: 20),
                    //     Column(
                    //       crossAxisAlignment: CrossAxisAlignment.start,
                    //       children: const [
                    //         Text16(
                    //           text: "아직 받지않은 보상이 있어요!",
                    //           bold: true,
                    //         ),
                    //         SizedBox(height: 3),
                    //         Text12(
                    //           text: "코인과 애정도를 받으세요.",
                    //           textColor: myGrey,
                    //         )
                    //       ],
                    //     ),
                    //   ],
                    // ),
                    GestureDetector(
                        onTap: () async {
                          Reward rewards = await ChallengeService.fetchReward();
                          print(rewards.data!.coin!);
                          print(rewards.data!.heart!);
                          setState(() {
                            reward = false;
                          });
                          Fluttertoast.showToast(
                            msg:
                                '${rewards.data!.coin!}개의 코인과 \n ${rewards.data!.heart!}의 애정도를 얻었어요!',
                            toastLength: Toast.LENGTH_SHORT,
                            gravity: ToastGravity.TOP,
                            backgroundColor: myMainGreen,
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
                        )
                        // Container(
                        //   decoration: BoxDecoration(
                        //       color: myLightGreen,
                        //       borderRadius:
                        //           BorderRadius.all(Radius.circular(5))),
                        //   padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                        //   child: Text16(
                        //     text: "받기",
                        //     textColor: Colors.white,
                        //   ),
                        // ),
                        )
                    //     ],
                    //   ),
                    // )
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
                              Navigator.of(context).push(
                                  SlidePageRoute(nextPage: ChallengePage()));
                            },
                            child: Container(
                              height: 90,
                              width: double.infinity,
                              padding: EdgeInsets.all(20),
                              margin: EdgeInsets.only(bottom: 16),
                              decoration: myBoxDecoration,
                              child: Text16(
                                text: "도전과제",
                                bold: true,
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
                              decoration: myBoxDecoration,
                              child: Text16(
                                text: "친구",
                                bold: true,
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
                            decoration: myBoxDecoration,
                            child: Text16(
                              text: "워치 or 날씨",
                              bold: true,
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
          image: AssetImage('assets/images/dashBoardImg.png'),
          fit: BoxFit.cover, // 이미지를 화면에 맞게 확장
        ),
        borderRadius: BorderRadius.only(
            topLeft: Radius.circular(20), topRight: Radius.circular(20)),
      ),
      child: SizedBox(
        height: double.infinity,
        width: double.infinity,
        child: Container(
            margin: EdgeInsets.fromLTRB(20, 60, 0, 0),
            child: Text(
              '$nickName님, 반가워요! \n오늘도 운동 시작해 볼까요?',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                height: 1.7,
              ),
            )),
      ),
    );
  }
}
