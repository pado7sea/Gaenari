import 'package:flutter/material.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class RecordScreen extends StatefulWidget {
  const RecordScreen({super.key});

  @override
  State<RecordScreen> createState() => _RecordScreenState();
}

class _RecordScreenState extends State<RecordScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SmallAppBar(
      title: '기록',
    ));
  }
}
