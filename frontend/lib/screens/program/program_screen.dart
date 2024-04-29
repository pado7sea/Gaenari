import 'package:flutter/material.dart';
import 'package:forsythia/widgets/largeAppBar.dart';

class ProgramScreen extends StatefulWidget {
  const ProgramScreen({super.key});

  @override
  State<ProgramScreen> createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: largeAppBar(
            title: '운동프로그램', sentence: '즐겨찾기를 해두면 \n 워치에서 바로 선택가능!'));
  }
}
