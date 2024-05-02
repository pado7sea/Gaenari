import 'package:flutter/material.dart';
import 'package:forsythia/screens/mate/search.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class AddMatePage extends StatefulWidget {
  const AddMatePage({super.key});

  @override
  State<AddMatePage> createState() => _AddMatePageState();
}

class ListItem {
  final String title;
  ListItem(this.title);
}

class _AddMatePageState extends State<AddMatePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: "친구검색",
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: const [
            SizedBox(height: 20), // 검색창과 텍스트 사이의 간격
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 20), // 검색창 좌우 마진
              child: MateSearchBar(),
            ),
            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
            Center(
              child: Text('친구의 닉네임을 검색하여 \n새로운 친구를 찾아보세요.'),
            ),
            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
          ],
        ),
      ),
    );
  }
}
