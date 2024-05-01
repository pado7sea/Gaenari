import 'package:flutter/material.dart';
import 'package:forsythia/screens/mate/mate_add.dart';
import 'package:forsythia/screens/mate/mate_new.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SlidePageRoute.dart';
import 'package:forsythia/widgets/box.dart';
import 'package:forsythia/widgets/largeAppBar.dart';

class MatePage extends StatefulWidget {
  const MatePage({super.key});

  @override
  State<MatePage> createState() => _MatePageState();
}

class ListItem {
  final String title;
  ListItem(this.title);
}

class _MatePageState extends State<MatePage> {
  final List<ListItem> _dataList = [
    ListItem('서민지'),
    ListItem('서민기'),
    ListItem('서민주'),
    ListItem('서만기'),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: largeAppBar(
        title: "친구",
        sentence: "당신의 친구 목록이 보이는 공간입니다. \n친구집에 놀러가보세요",
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            _topButtons(),
            SizedBox(height: 16),
            _mates(),
          ],
        ),
      ),
    );
  }

  // 상단의 버튼들
  Widget _topButtons() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        GestureDetector(
          onTap: () {
            Navigator.of(context).push(SlidePageRoute(nextPage: NewMatePage()));
          },
          child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Text16(
                  text: "친구요청 ",
                ),
                Image.asset(
                  "assets/icons/mate_new.png",
                  filterQuality: FilterQuality.none,
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
        ),
        GestureDetector(
          onTap: () {
            Navigator.of(context).push(SlidePageRoute(nextPage: AddMatePage()));
          },
          child: Container(
            padding: EdgeInsets.fromLTRB(10, 20, 16, 0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Text16(
                  text: "친구검색 ",
                ),
                Image.asset(
                  "assets/icons/mate_add.png",
                  filterQuality: FilterQuality.none,
                  width: 25,
                  height: 25,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
        ),
        SizedBox(width: 16),
      ],
    );
  }

  Widget _mates() {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 20),
      child: ListView.builder(
        shrinkWrap: true, // 필요한 만큼의 공간만 차지하도록 설정
        itemCount: _dataList.length,
        itemBuilder: (BuildContext context, int index) {
          return Dismissible(
            key: Key(_dataList[index].title), // 고유한 키 값
            onDismissed: (direction) {
              setState(() {
                _dataList.removeAt(index); // 아이템 삭제
              });
            },
            background: Container(
              margin: EdgeInsets.fromLTRB(0, 0, 0, 16),
              decoration: myBoxDecoration,
              // color: Colors.red,
              alignment: Alignment.centerRight,
              padding: EdgeInsets.only(right: 20.0),
              child: Icon(Icons.delete),
            ),
            child: Container(
              decoration: myBoxDecoration,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 16),
              child: ListTile(
                title: Text(_dataList[index].title),
              ),
            ),
          );
        },
      ),
    );
  }
}
