import 'package:flutter/material.dart';
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
          title: "친구요청",
          back: true,
        ),
        body: SingleChildScrollView(
            child: Column(
          children: [
            Row(
              children: [_tapbutton(0, "받은요청"), _tapbutton(1, "보낸요청")],
            ),
            SizedBox(height: 16),
            Center(
              child: tapbar == 0 ? Text('받은요청') : Text("보낸요청"),
            ),
          ],
        )));
  }

  // tap부분
  Widget _tapbutton(tapnum, taptext) {
    return GestureDetector(
        onTap: () {
          setState(() {
            tapbar = tapnum;
          });
        },
        child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Text16(
              text: taptext,
            )));
  }

  // 리스트부분 - 해당하는 리스트를 파라미터로 받음
  // 아직 쓰지 않아서 파란줄 뜸!!
  Widget _mates(list) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 20),
      child: ListView.builder(
        shrinkWrap: true, // 필요한 만큼의 공간만 차지하도록 설정
        itemCount: list.length,
        itemBuilder: (BuildContext context, int index) {
          return Container(
            decoration: myBoxDecoration,
            margin: EdgeInsets.fromLTRB(0, 0, 0, 16),
            child: ListTile(
              title: Text(list[index].title),
            ),
          );
        },
      ),
    );
  }
}
