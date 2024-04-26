import 'package:flutter/material.dart';
import 'package:forsythia/screens/mate/search.dart';
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
    ListItem('Item 1'),
    ListItem('Item 2'),
    ListItem('Item 3'),
    ListItem('Item 4'),
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
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 16), // 검색창 좌우 마진
              child: MateSearchBar(),
            ),
            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
            Center(
              child: Text('친구가...'),
            ),

            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
            Center(
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
                      color: Colors.red,
                      alignment: Alignment.centerRight,
                      padding: EdgeInsets.only(right: 20.0),
                      child: Icon(Icons.delete),
                    ),
                    child: ListTile(
                      title: Text(_dataList[index].title),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
