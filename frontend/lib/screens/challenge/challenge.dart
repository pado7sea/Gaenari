import 'package:flutter/material.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/largeAppBar.dart';

class ChallengePage extends StatefulWidget {
  const ChallengePage({super.key});

  @override
  State<ChallengePage> createState() => _ChallengePageState();
}

class ListItem {
  final String title;
  ListItem(this.title);
}

class _ChallengePageState extends State<ChallengePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: largeAppBar(
        title: "도전과제",
        sentence: "도전과제를 달성하고 \n많은 보상과 강아지를 레벨업",
      ),
      body: SingleChildScrollView(
          child: Container(
        alignment: Alignment.center,
        child: Text36(text: "도전과제"),
      )),
    );
  }
}
