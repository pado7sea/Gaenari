import 'package:flutter/material.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class bodyinfoScreen extends StatefulWidget {
  const bodyinfoScreen({super.key});

  @override
  State<bodyinfoScreen> createState() => _bodyinfoScreenState();
}

class _bodyinfoScreenState extends State<bodyinfoScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(title: '신체정보'),
    );
  }
}
