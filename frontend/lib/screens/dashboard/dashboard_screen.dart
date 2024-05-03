import 'package:flutter/material.dart';
import 'package:forsythia/provider/login_info_provider.dart';
import 'package:forsythia/screens/challenge/challenge_screen.dart';
import 'package:forsythia/screens/mate/mate_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:draggable_bottom_sheet/draggable_bottom_sheet.dart';
import 'package:provider/provider.dart';

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
  @override
  Widget build(BuildContext context) {
    var nickName = Provider.of<LoginInfoProvider>(context).loginInfo?.nickname;
    return Scaffold(
      body: DraggableBottomSheet(
        minExtent: 50,
        useSafeArea: false,
        curve: Curves.easeIn,
        barrierColor: Colors.transparent,
        previewWidget: _previewWidget(),
        expandedWidget: _expandedWidget(),
        backgroundWidget: _backgroundWidget(nickName),
        maxExtent: MediaQuery.of(context).size.height * 0.76,
        onDragging: (pos) {},
      ),
    );
  }

  Widget _previewWidget() {
    return Container(
      padding: const EdgeInsets.only(top: 16),
      decoration: const BoxDecoration(
        color: myBackground,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(20),
          topRight: Radius.circular(20),
        ),
      ),
      width: double.infinity,
      child: Column(
        children: <Widget>[
          Container(
            width: 40,
            height: 6,
            decoration: BoxDecoration(
              color: const Color.fromARGB(255, 95, 95, 95),
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          const SizedBox(height: 8),
        ],
      ),
    );
  }

  Widget _backgroundWidget(String? nickName) {
    return Scaffold(
      body: Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              image: AssetImage('assets/images/dashBoardImg.png'),
              fit: BoxFit.cover, // 이미지를 화면에 맞게 확장
            ),
          ),
          child: SizedBox(
            height: double.infinity,
            width: double.infinity,
            child: Container(
              margin: EdgeInsets.fromLTRB(20, 50, 0, 0),
              child: Text20(
                // text: ' ${Provider.of<TokenProvider>(context).token}',
                text: '$nickName님! 반가워요! 운동시작해볼까요?',
                bold: true,
              ),
            ),
          )),
    );
  }

  Widget _expandedWidget() {
    return Container(
      padding: const EdgeInsets.only(top: 16, bottom: 0),
      decoration: const BoxDecoration(
        color: myBackground,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(20),
          topRight: Radius.circular(20),
        ),
      ),
      width: double.infinity,
      child: Column(
        children: <Widget>[
          Container(
            width: 40,
            height: 6,
            decoration: BoxDecoration(
              color: myGrey,
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          const SizedBox(height: 8),
          Expanded(
            child: GridView(
              physics: const NeverScrollableScrollPhysics(),
              padding: EdgeInsets.only(top: 0),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 1,
                crossAxisSpacing: 10,
                childAspectRatio: (25 / 360),
              ),
              children: [
                Container(
                  padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
                  color: myBackground,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        height: 60,
                        width: double.infinity,
                        padding: EdgeInsets.all(10),
                        margin: EdgeInsets.only(bottom: 16),
                        decoration: myBoxDecoration,
                        child: Text16(
                          text: "아직 받지않은 보상이 있다요",
                        ),
                      ),
                      Container(
                        height: 200,
                        width: double.infinity,
                        padding: EdgeInsets.all(10),
                        margin: EdgeInsets.only(bottom: 16),
                        decoration: myBoxDecoration,
                        child: Text16(
                          text: "주간기록",
                        ),
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
                                    Navigator.of(context).push(SlidePageRoute(
                                        nextPage: ChallengePage()));
                                  },
                                  child: Container(
                                    height: 120,
                                    width: double.infinity,
                                    padding: EdgeInsets.all(10),
                                    margin: EdgeInsets.only(bottom: 16),
                                    decoration: myBoxDecoration,
                                    child: Text16(
                                      text: "도전과제",
                                    ),
                                  ),
                                ),
                                GestureDetector(
                                  onTap: () {
                                    Navigator.of(context).push(
                                        SlidePageRoute(nextPage: MatePage()));
                                  },
                                  child: Container(
                                    height: 120,
                                    width: double.infinity,
                                    padding: EdgeInsets.all(10),
                                    // margin: EdgeInsets.only(bottom: 10),
                                    decoration: myBoxDecoration,
                                    child: Text16(
                                      text: "친구",
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Expanded(
                            flex: 6,
                            child: Container(
                              height: 256,
                              padding: EdgeInsets.fromLTRB(10, 10, 10, 40),
                              margin: EdgeInsets.only(left: 16),
                              decoration: myBoxDecoration,
                              child: Text16(
                                text: "워치 or 날씨",
                              ),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
