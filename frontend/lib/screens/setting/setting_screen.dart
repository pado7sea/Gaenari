import 'package:flutter/material.dart';
import 'package:forsythia/widgets/box.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class SettingScreen extends StatefulWidget {
  const SettingScreen({super.key});

  @override
  State<SettingScreen> createState() => _SettingScreenState();
}

class _SettingScreenState extends State<SettingScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(title: '설정'),
      body: Column(
        children: [
          Container(
            child: Text('hi'),
            decoration: myBoxDecoration,
            width: double.infinity,
            padding: EdgeInsets.all(30),
            margin: EdgeInsets.fromLTRB(20, 10, 20, 0),
          ),
          Container(
            child: Text('hi'),
            width: double.infinity,
            decoration: myBoxDecoration,
            padding: EdgeInsets.all(30),
            margin: EdgeInsets.fromLTRB(20, 10, 20, 0),
          ),
          Container(
            child: Text('hi'),
            decoration: myBoxDecoration,
            width: double.infinity,
            padding: EdgeInsets.all(30),
            margin: EdgeInsets.fromLTRB(20, 10, 20, 0),
          ),
          Container(
            child: Text('hi'),
            decoration: myBoxDecoration,
            width: double.infinity,
            padding: EdgeInsets.all(30),
            margin: EdgeInsets.fromLTRB(20, 10, 20, 0),
          ),
        ],
      ),
    );
  }
}
