import 'package:flutter/material.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/screens/challenge/challenge_screen.dart';
import 'package:forsythia/screens/mate/mate_screen.dart';
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
  bool a = false;
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
            return SlidingUpPanel(
              panel: _panelWidget(),
              collapsed: _collapsed(),
              body: _backgroundWidget(nickName),
              minHeight: a ? 150 : 50,
              maxHeight: MediaQuery.of(context).size.height - 270,
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(20), topRight: Radius.circular(20)),
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
                              Navigator.of(context).push(
                                  SlidePageRoute(nextPage: ChallengePage()));
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
                              Navigator.of(context)
                                  .push(SlidePageRoute(nextPage: MatePage()));
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
              text: "스크롤을 올려 대시보드를 확인하세요",
              textColor: myGrey,
            ),
            SizedBox(height: a ? 16 : 0),
            a
                ? Container(
                    height: 60,
                    width: double.infinity,
                    padding: EdgeInsets.all(10),
                    margin: EdgeInsets.only(bottom: 16),
                    decoration: myBoxDecoration,
                    child: Text16(
                      text: "아직 받지않은 보상이 있다요",
                    ),
                  )
                : SizedBox(
                    height: 0,
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
          margin: EdgeInsets.fromLTRB(20, 50, 0, 0),
          child: Text20(
            // text: ' ${Provider.of<TokenProvider>(context).token}',
            text: '$nickName님, 반가워요! \n오늘도 운동 시작해 볼까요?',
            bold: true,
          ),
        ),
      ),
    );
  }
}
