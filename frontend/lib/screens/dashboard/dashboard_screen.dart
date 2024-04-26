import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:forsythia/screens/mate/mate.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box.dart';
import 'package:forsythia/widgets/button.dart';

class DashBoardScreen extends StatefulWidget {
  const DashBoardScreen({super.key});

  @override
  State<DashBoardScreen> createState() => DashBoardScreenState();
}

class DashBoardScreenState extends State<DashBoardScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('대시보드'),
      ),
      body: Container(
        padding: EdgeInsets.all(16),
        decoration: myBoxDecoration,
        height: 120,
        child: Column(
          children: [
            Text25(text: "네비게이트목록"),
            Row(
              children: [
                SmallButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      CupertinoPageRoute(builder: (context) => MatePage()),
                    );
                  },
                  text: '친구 버튼',
                  active: true,
                ),
                SmallButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => MatePage()),
                    );
                  },
                  text: '친구 버튼',
                  active: true,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
