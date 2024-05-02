import 'package:flutter/material.dart';
import 'package:forsythia/widgets/SmallAppBar.dart';

class RecodScreen extends StatefulWidget {
  const RecodScreen({super.key});

  @override
  State<RecodScreen> createState() => _RecodScreenState();
}

class _RecodScreenState extends State<RecodScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
      title: '기록',
    ));
  }
}
