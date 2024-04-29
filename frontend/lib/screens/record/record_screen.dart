import 'package:flutter/material.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class RecodScreen extends StatefulWidget {
  const RecodScreen({super.key});

  @override
  State<RecodScreen> createState() => _RecodScreenState();
}

class _RecodScreenState extends State<RecodScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: smallAppBar(
      title: '기록',
    ));
  }
}
