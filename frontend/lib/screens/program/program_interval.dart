import 'package:flutter/material.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class AddIntervalProgramPage extends StatefulWidget {
  const AddIntervalProgramPage({super.key});

  @override
  State<AddIntervalProgramPage> createState() => _AddIntervalProgramPageState();
}

class _AddIntervalProgramPageState extends State<AddIntervalProgramPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
          title: "인터벌목표",
          back: true,
        ),
        body: Column(children: const [
          SizedBox(height: 20), // 검색창과 텍스트 사이의 간격
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 20), // 검색창 좌우 마진
          ),
          SizedBox(height: 16),
          Text("여기 내용"),
          SizedBox(height: 20),
        ]));
  }
}
