import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class ErrorScreen extends StatefulWidget {
  const ErrorScreen({super.key});

  @override
  State<ErrorScreen> createState() => _ErrorScreenState();
}

class _ErrorScreenState extends State<ErrorScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '에러', back: true),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.warning_rounded,
              size: 40,
              color: myRed,
            ),
            SizedBox(height: 20),
            Text16(text: '인터넷 연결이 필요합니다.'),
            SizedBox(height: 40),
          ],
        ),
      ),
    );
  }
}
