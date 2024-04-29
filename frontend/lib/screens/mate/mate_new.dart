import 'package:flutter/material.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class NewMatePage extends StatefulWidget {
  const NewMatePage({super.key});

  @override
  State<NewMatePage> createState() => _NewMatePageState();
}

class ListItem {
  final String title;
  ListItem(this.title);
}

class _NewMatePageState extends State<NewMatePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
        title: "친구요청",
      ),
      body: SingleChildScrollView(
        child: Column(
          children: const [
            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
            Center(
              child: Text('친구가...'),
            ),
            SizedBox(height: 16), // 검색창과 텍스트 사이의 간격
          ],
        ),
      ),
    );
  }
}
