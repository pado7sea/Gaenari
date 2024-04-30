import 'package:flutter/material.dart';
import 'package:forsythia/screens/signup/signup_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class notificationScreen extends StatefulWidget {
  const notificationScreen({super.key});

  @override
  State<notificationScreen> createState() => _notificationScreenState();
}

class _notificationScreenState extends State<notificationScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
        title: '알림',
        back: true,
      ),
    );
  }
}
