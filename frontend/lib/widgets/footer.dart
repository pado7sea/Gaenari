import 'package:flutter/material.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onItemSelected;

  const CustomBottomNavigationBar({
    super.key,
    required this.selectedIndex,
    required this.onItemSelected,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(15, 0, 15, 20),
      decoration: BoxDecoration(
          color: Colors.white.withOpacity(0.7),
          borderRadius: BorderRadius.circular(100.0),
          boxShadow: [
            BoxShadow(
              color: Color(0xffBFC2C8).withOpacity(0.25),
              blurRadius: 15,
              offset: Offset(0, 10),
            ),
          ]),
      height: 70.0,
      child: ClipRRect(
        borderRadius: BorderRadius.circular(100.0),
        child: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          backgroundColor: Colors.transparent,
          showSelectedLabels: false,
          showUnselectedLabels: false,
          items: <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: SizedBox(
                width: 30,
                height: 30,
                child: Image.asset(
                  selectedIndex == 0
                      ? 'assets/bottom/active_dashboard.png'
                      : 'assets/bottom/none_dashboard.png',
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
              ),
              label: '',
            ),
            BottomNavigationBarItem(
              icon: SizedBox(
                width: 30,
                height: 30,
                child: Image.asset(
                  selectedIndex == 1
                      ? 'assets/bottom/active_record.png'
                      : 'assets/bottom/none_record.png',
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
              ),
              label: '',
            ),
            BottomNavigationBarItem(
              icon: SizedBox(
                width: 30,
                height: 30,
                child: Image.asset(
                  selectedIndex == 2
                      ? 'assets/bottom/active_program.png'
                      : 'assets/bottom/none_program.png',
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
              ),
              label: '',
            ),
            BottomNavigationBarItem(
              icon: SizedBox(
                width: 30,
                height: 30,
                child: Image.asset(
                  selectedIndex == 3
                      ? 'assets/bottom/active_doghouse.png'
                      : 'assets/bottom/none_doghouse.png',
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
              ),
              label: '',
            ),
            BottomNavigationBarItem(
              icon: SizedBox(
                width: 30,
                height: 30,
                child: Image.asset(
                  selectedIndex == 4
                      ? 'assets/bottom/active_setting.png'
                      : 'assets/bottom/none_setting.png',
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
              ),
              label: '',
            ),
          ],
          currentIndex: selectedIndex,
          // selectedItemColor: mainWhite,
          // unselectedItemColor: mainWhite,
          onTap: onItemSelected,
          elevation: 0,
        ),
      ),
    );
  }
}

// animated_indexed_stack.dart 파일 내용

// ignore: constant_identifier_names
const double INIT_POSITION = 1.0;

class AnimatedIndexedStack extends StatefulWidget {
  final int index;
  final List<Widget> children;
  final Duration duration;

  const AnimatedIndexedStack({
    super.key,
    required this.index,
    required this.children,
    this.duration = const Duration(milliseconds: 200),
  });

  @override
  // ignore: library_private_types_in_public_api
  _AnimatedIndexedStackState createState() => _AnimatedIndexedStackState();
}

class _AnimatedIndexedStackState extends State<AnimatedIndexedStack>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  double _position = INIT_POSITION;

  @override
  void didUpdateWidget(covariant AnimatedIndexedStack oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.index != oldWidget.index) {
      bool isRightScreen = widget.index > oldWidget.index;
      setState(() {
        _position = isRightScreen ? INIT_POSITION : -INIT_POSITION;
      });
      _controller.forward(from: 0.0);
    }
  }

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: widget.duration,
    );
    _controller.forward();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FadeTransition(
      opacity: _controller,
      child: SlideTransition(
        position: Tween<Offset>(
          begin: Offset(_position, 0.0),
          end: Offset.zero,
        ).animate(_controller),
        child: IndexedStack(
          index: widget.index,
          children: widget.children,
        ),
      ),
    );
  }
}
