import 'package:flutter/material.dart';
import 'package:forsythia/widgets/largeAppBar.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class ProgramScreen extends StatefulWidget {
  const ProgramScreen({super.key});

  @override
  State<ProgramScreen> createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: smallAppBar(
            title: '운동프로그램'));
  }
}
