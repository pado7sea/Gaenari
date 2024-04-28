import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:forsythia/screens/challenge/challenge.dart';
import 'package:forsythia/screens/mate/mate.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box.dart';
import 'package:forsythia/widgets/button.dart';
import 'package:draggable_bottom_sheet/draggable_bottom_sheet.dart';

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
    return Scaffold(
      body: DraggableBottomSheet(
        minExtent: 100,
        useSafeArea: false,
        curve: Curves.easeIn,
        barrierColor: Colors.transparent,
        previewWidget: _expandedWidget(),
        expandedWidget: _expandedWidget(),
        backgroundWidget: _backgroundWidget(),
        maxExtent: MediaQuery.of(context).size.height * 0.8,
        onDragging: (pos) {},
      ),
      // child: Column(
      //   children: [
      //     Text25(text: "네비게이트목록"),
      //     Row(
      //       children: [
      //         SmallButton(
      //           onPressed: () {
      //             Navigator.push(
      //               context,
      //               CupertinoPageRoute(builder: (context) => MatePage()),
      //             );
      //           },
      //           text: '친구 버튼',
      //           active: true,
      //         ),
      //         SmallButton(
      //           onPressed: () {
      //             Navigator.push(
      //               context,
      //               MaterialPageRoute(builder: (context) => MatePage()),
      //             );
      //           },
      //           text: '친구 버튼',
      //           active: true,
      //         ),
      //       ],
      //     ),
      //   ],
      // ),
    );
  }

  Widget _backgroundWidget() {
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
            child: Text25(
              text: "재신님 방가방가 하이루",
            ),
          ),
        ),
      ),
    );
  }

  Widget _expandedWidget() {
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
          // const Text(
          //   '대충 패널 올라가고 내려감',
          //   style: TextStyle(
          //     color: Color.fromARGB(255, 128, 128, 128),
          //     fontSize: 16,
          //     fontWeight: FontWeight.bold,
          //   ),
          // ),
          // const SizedBox(height: 16),
          Expanded(
            child: GridView(
              padding: EdgeInsets.only(top: 0),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 1,
                crossAxisSpacing: 10,
                childAspectRatio: (25 / 350),
              ),
              children: [
                Container(
                  padding: const EdgeInsets.all(16),
                  color: myBackground,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        height: 60,
                        width: double.infinity,
                        padding: EdgeInsets.all(10),
                        margin: EdgeInsets.only(bottom: 10),
                        decoration: myBoxDecoration,
                        child: Text16(
                          text: "아직 받지않은 보상이 있다요",
                        ),
                      ),
                      Container(
                        height: 200,
                        width: double.infinity,
                        padding: EdgeInsets.all(10),
                        margin: EdgeInsets.only(bottom: 10),
                        decoration: myBoxDecoration,
                        child: Text20(
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
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              ChallengePage()),
                                    );
                                  },
                                  child: Container(
                                    height: 150,
                                    width: double.infinity,
                                    padding: EdgeInsets.all(10),
                                    margin: EdgeInsets.only(bottom: 10),
                                    decoration: myBoxDecoration,
                                    child: Text20(
                                      text: "도전과제",
                                    ),
                                  ),
                                ),
                                GestureDetector(
                                  onTap: () {
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => MatePage()),
                                    );
                                  },
                                  child: Container(
                                    height: 150,
                                    width: double.infinity,
                                    padding: EdgeInsets.all(10),
                                    // margin: EdgeInsets.only(bottom: 10),
                                    decoration: myBoxDecoration,
                                    child: Text20(
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
                              height: 310,
                              padding: EdgeInsets.all(10),
                              margin: EdgeInsets.only(bottom: 10, left: 10),
                              decoration: myBoxDecoration,
                              child: Text20(
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
