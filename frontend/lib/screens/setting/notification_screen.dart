import 'package:flutter/material.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class norificationScreen extends StatefulWidget {
  const norificationScreen({super.key});

  @override
  State<norificationScreen> createState() => _norificationScreenState();
}

class _norificationScreenState extends State<norificationScreen> {
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
