import 'package:flutter/material.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class accountScreen extends StatefulWidget {
  const accountScreen({super.key});

  @override
  State<accountScreen> createState() => _accountScreenState();
}

class _accountScreenState extends State<accountScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(title: '계정'),
    );
  }
}
