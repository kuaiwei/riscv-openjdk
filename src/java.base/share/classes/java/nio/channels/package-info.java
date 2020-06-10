/*
 * Copyright (c) 2001, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * Defines channels, which represent connections to entities that are capable of
 * performing I/O operations, such as files and sockets; defines selectors, for
 * multiplexed, non-blocking I/O operations.
 *
 * <a id="channels"></a>
 *
 * <table class="striped" style="text-align:left; margin-left:2em">
 *     <caption style="display:none">Lists channels and their descriptions</caption>
 * <thead>
 * <tr><th scope="col">Channels</th>
 *     <th scope="col">Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><th scope="row"><i>{@link java.nio.channels.Channel}</i></th>
 *     <td>A nexus for I/O operations</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:1em"><i>{@link java.nio.channels.ReadableByteChannel}</i></span></th>
 *     <td>Can read into a buffer</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em"><i>{@link java.nio.channels.ScatteringByteChannel}</i></span></th>
 *     <td>Can read into a sequence of buffers</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:1em"><i>{@link java.nio.channels.WritableByteChannel}</i></span></th>
 *     <td>Can write from a buffer</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em"><i>{@link java.nio.channels.GatheringByteChannel}</i></span></th>
 *     <td>Can write from a sequence of buffers</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:1em"><i>{@link java.nio.channels.ByteChannel}</i></span></th>
 *     <td>Can read/write to/from a buffer</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em"><i>{@link java.nio.channels.SeekableByteChannel}</i></span></th>
 *     <td>A {@code ByteChannel} connected to an entity that contains a variable-length
 *         sequence of bytes</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:1em"><i>{@link java.nio.channels.AsynchronousChannel}</i></span></th>
 *     <td>Supports asynchronous I/O operations.</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em"><i>{@link java.nio.channels.AsynchronousByteChannel}</i></span></th>
 *     <td>Can read and write bytes asynchronously</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:1em"><i>{@link java.nio.channels.NetworkChannel}</i></span></th>
 *     <td>A channel to a network socket</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em"><i>{@link java.nio.channels.MulticastChannel}</i></span></th>
 *     <td>Can join Internet Protocol (IP) multicast groups</td></tr>
 * <tr><th scope="row">{@link java.nio.channels.Channels}</th>
 *     <td>Utility methods for channel/stream interoperation</td></tr>
 * </tbody>
 * </table>
 *
 * <p> A <i>channel</i> represents an open connection to an entity such as a
 * hardware device, a file, a network socket, or a program component that is
 * capable of performing one or more distinct I/O operations, for example reading
 * or writing.  As specified in the {@link java.nio.channels.Channel} interface,
 * channels are either open or closed, and they are both <i>asynchronously
 * closeable</i> and <i>interruptible</i>.
 *
 * <p> The {@link java.nio.channels.Channel} interface is extended by several
 * other interfaces.
 *
 * <p> The {@link java.nio.channels.ReadableByteChannel} interface specifies a
 * {@link java.nio.channels.ReadableByteChannel#read read} method that reads bytes
 * from the channel into a buffer; similarly, the {@link
 * java.nio.channels.WritableByteChannel} interface specifies a {@link
 * java.nio.channels.WritableByteChannel#write write} method that writes bytes
 * from a buffer to the channel. The {@link java.nio.channels.ByteChannel}
 * interface unifies these two interfaces for the common case of channels that can
 * both read and write bytes. The {@link java.nio.channels.SeekableByteChannel}
 * interface extends the {@code ByteChannel} interface with methods to {@link
 * java.nio.channels.SeekableByteChannel#position() query} and {@link
 * java.nio.channels.SeekableByteChannel#position(long) modify} the channel's
 * current position, and its {@link java.nio.channels.SeekableByteChannel#size
 * size}.
 *
 * <p> The {@link java.nio.channels.ScatteringByteChannel} and {@link
 * java.nio.channels.GatheringByteChannel} interfaces extend the {@link
 * java.nio.channels.ReadableByteChannel} and {@link
 * java.nio.channels.WritableByteChannel} interfaces, respectively, adding {@link
 * java.nio.channels.ScatteringByteChannel#read read} and {@link
 * java.nio.channels.GatheringByteChannel#write write} methods that take a
 * sequence of buffers rather than a single buffer.
 *
 * <p> The {@link java.nio.channels.NetworkChannel} interface specifies methods
 * to {@link java.nio.channels.NetworkChannel#bind bind} the channel's socket,
 * obtain the address to which the socket is bound, and methods to {@link
 * java.nio.channels.NetworkChannel#getOption get} and {@link
 * java.nio.channels.NetworkChannel#setOption set} socket options. The {@link
 * java.nio.channels.MulticastChannel} interface specifies methods to join
 * Internet Protocol (IP) multicast groups.
 *
 * <p> The {@link java.nio.channels.Channels} utility class defines static methods
 * that support the interoperation of the stream classes of the {@link
 * java.io} package with the channel classes of this package.  An appropriate
 * channel can be constructed from an {@link java.io.InputStream} or an {@link
 * java.io.OutputStream}, and conversely an {@link java.io.InputStream} or an
 * {@link java.io.OutputStream} can be constructed from a channel.  A {@link
 * java.io.Reader} can be constructed that uses a given charset to decode bytes
 * from a given readable byte channel, and conversely a {@link java.io.Writer} can
 * be constructed that uses a given charset to encode characters into bytes and
 * write them to a given writable byte channel.
 *
 * <table class="striped" style="margin-left:2em; text-align:left">
 *     <caption style="display:none">
 *         Lists file channels and their descriptions</caption>
 * <thead>
 * <tr><th scope="col">File channels</th>
 *     <th scope="col">Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><th scope="row">
 *     {@link java.nio.channels.FileChannel}</th>
 *     <td>Reads, writes, maps, and manipulates files</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.channels.FileLock}</th>
 *     <td>A lock on a (region of a) file</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.MappedByteBuffer}</th>
 *     <td>A direct byte buffer mapped to a region of a file</td></tr>
 * </tbody>
 * </table>
 *
 * <p> The {@link java.nio.channels.FileChannel} class supports the usual
 * operations of reading bytes from, and writing bytes to, a channel connected to
 * a file, as well as those of querying and modifying the current file position
 * and truncating the file to a specific size.  It defines methods for acquiring
 * locks on the whole file or on a specific region of a file; these methods return
 * instances of the {@link java.nio.channels.FileLock} class.  Finally, it defines
 * methods for forcing updates to the file to be written to the storage device that
 * contains it, for efficiently transferring bytes between the file and other
 * channels, and for mapping a region of the file directly into memory.
 *
 * <p> A {@code FileChannel} is created by invoking one of its static {@link
 * java.nio.channels.FileChannel#open open} methods, or by invoking the {@code
 * getChannel} method of a {@link java.io.FileInputStream}, {@link
 * java.io.FileOutputStream}, or {@link java.io.RandomAccessFile} to return a
 * file channel connected to the same underlying file as the {@link java.io}
 * class.
 *
 * <a id="multiplex"></a>
 * <table class="striped" style="margin-left:2em; text-align:left">
 *     <caption style="display:none">
 *         Lists multiplexed, non-blocking channels and their descriptions</caption>
 * <thead>
 * <tr><th scope="col">Multiplexed, non-blocking I/O</th>
 *     <th scope="col">Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><th scope="row">{@link java.nio.channels.SelectableChannel}</th>
 *     <td>A channel that can be multiplexed</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em">{@link java.nio.channels.DatagramChannel}</span></th>
 *     <td>A channel to a datagram-oriented socket</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em">{@link java.nio.channels.Pipe.SinkChannel}</span></th>
 *     <td>The write end of a pipe</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em">{@link java.nio.channels.Pipe.SourceChannel}</span></th>
 *     <td>The read end of a pipe</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em">{@link java.nio.channels.ServerSocketChannel}</span></th>
 *     <td>A channel to a stream-oriented listening socket</td></tr>
 * <tr><th scope="row">
 *     <span style="padding-left:2em">{@link java.nio.channels.SocketChannel}</span></th>
 *     <td>A channel for a stream-oriented connecting socket</td></tr>
 * <tr><th scope="row">{@link java.nio.channels.Selector}</th>
 *     <td>A multiplexor of selectable channels</td></tr>
 * <tr><th scope="row">{@link java.nio.channels.SelectionKey}</th>
 *     <td>A token representing the registration of a channel
 *     with a selector</td></tr>
 * <tr><th scope="row">{@link java.nio.channels.Pipe}</th>
 *     <td>Two channels that form a unidirectional pipe</td></tr>
 * </tbody>
 * </table>
 *
 * <p> Multiplexed, non-blocking I/O, which is much more scalable than
 * thread-oriented, blocking I/O, is provided by <i>selectors</i>, <i>selectable
 * channels</i>, and <i>selection keys</i>.
 *
 * <p> A <a href="Selector.html"><i>selector</i></a> is a multiplexor of <a
 * href="SelectableChannel.html"><i>selectable channels</i></a>, which in turn are
 * a special type of channel that can be put into <a
 * href="SelectableChannel.html#bm"><i>non-blocking mode</i></a>.  To perform
 * multiplexed I/O operations, one or more selectable channels are first created,
 * put into non-blocking mode, and {@link
 * java.nio.channels.SelectableChannel#register <i>registered</i>}
 * with a selector.  Registering a channel specifies the set of I/O operations
 * that will be tested for readiness by the selector, and returns a <a
 * href="SelectionKey.html"><i>selection key</i></a> that represents the
 * registration.
 *
 * <p> Once some channels have been registered with a selector, a <a
 * href="Selector.html#selop"><i>selection operation</i></a> can be performed in
 * order to discover which channels, if any, have become ready to perform one or
 * more of the operations in which interest was previously declared.  If a channel
 * is ready then the key returned when it was registered will be added to the
 * selector's <i>selected-key set</i>.  The key set, and the keys within it, can
 * be examined in order to determine the operations for which each channel is
 * ready.  From each key one can retrieve the corresponding channel in order to
 * perform whatever I/O operations are required.
 *
 * <p> That a selection key indicates that its channel is ready for some operation
 * is a hint, but not a guarantee, that such an operation can be performed by a
 * thread without causing the thread to block.  It is imperative that code that
 * performs multiplexed I/O be written so as to ignore these hints when they prove
 * to be incorrect.
 *
 * <p> This package defines selectable-channel classes corresponding to the {@link
 * java.net.DatagramSocket}, {@link java.net.ServerSocket}, and {@link
 * java.net.Socket} classes defined in the {@link java.net} package.
 * Minor changes to these classes have been made in order to support sockets that
 * are associated with channels.  This package also defines a simple class that
 * implements unidirectional pipes.  In all cases, a new selectable channel is
 * created by invoking the static {@code open} method of the corresponding class.
 * If a channel needs an associated socket then a socket will be created as a side
 * effect of this operation.
 *
 * <p> The implementation of selectors, selectable channels, and selection keys
 * can be replaced by "plugging in" an alternative definition or instance of the
 * {@link java.nio.channels.spi.SelectorProvider} class defined in the {@link
 * java.nio.channels.spi} package.  It is not expected that many developers
 * will actually make use of this facility; it is provided primarily so that
 * sophisticated users can take advantage of operating-system-specific
 * I/O-multiplexing mechanisms when very high performance is required.
 *
 * <p> Much of the bookkeeping and synchronization required to implement the
 * multiplexed-I/O abstractions is performed by the {@link
 * java.nio.channels.spi.AbstractInterruptibleChannel}, {@link
 * java.nio.channels.spi.AbstractSelectableChannel}, {@link
 * java.nio.channels.spi.AbstractSelectionKey}, and {@link
 * java.nio.channels.spi.AbstractSelector} classes in the {@link
 * java.nio.channels.spi} package.  When defining a custom selector provider,
 * only the {@link java.nio.channels.spi.AbstractSelector} and {@link
 * java.nio.channels.spi.AbstractSelectionKey} classes should be subclassed
 * directly; custom channel classes should extend the appropriate {@link
 * java.nio.channels.SelectableChannel} subclasses defined in this package.
 *
 * <a id="async"></a>
 *
 * <table class="striped" style="padding-left:2em; text-align:left">
 *     <caption style="display:none">
 *         Lists asynchronous channels and their descriptions</caption>
 * <thead>
 * <tr><th scope="col">Asynchronous I/O</th>
 *     <th scope="col">Description</th></tr>
 * </thead>
 * <tbody>
 * <tr><th scope="row">
 *     {@link java.nio.channels.AsynchronousFileChannel}</th>
 *     <td>An asynchronous channel for reading, writing, and manipulating a file</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.channels.AsynchronousSocketChannel}</th>
 *     <td>An asynchronous channel to a stream-oriented connecting socket</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.channels.AsynchronousServerSocketChannel}</th>
 *     <td>An asynchronous channel to a stream-oriented listening socket</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.channels.CompletionHandler}</th>
 *     <td>A handler for consuming the result of an asynchronous operation</td></tr>
 * <tr><th scope="row">
 *     {@link java.nio.channels.AsynchronousChannelGroup}</th>
 *     <td>A grouping of asynchronous channels for the purpose of resource sharing</td></tr>
 * </tbody>
 * </table>
 *
 * <p> {@link java.nio.channels.AsynchronousChannel Asynchronous channels} are a
 * special type of channel capable of asynchronous I/O operations. Asynchronous
 * channels are non-blocking and define methods to initiate asynchronous
 * operations, returning a {@link java.util.concurrent.Future} representing the
 * pending result of each operation. The {@code Future} can be used to poll or
 * wait for the result of the operation. Asynchronous I/O operations can also
 * specify a {@link java.nio.channels.CompletionHandler} to invoke when the
 * operation completes. A completion handler is user provided code that is executed
 * to consume the result of I/O operation.
 *
 * <p> This package defines asynchronous-channel classes that are connected to
 * a stream-oriented connecting or listening socket, or a datagram-oriented socket.
 * It also defines the {@link java.nio.channels.AsynchronousFileChannel} class
 * for asynchronous reading, writing, and manipulating a file. As with the {@link
 * java.nio.channels.FileChannel} it supports operations to truncate the file
 * to a specific size, force updates to the file to be written to the storage
 * device, or acquire locks on the whole file or on a specific region of the file.
 * Unlike the {@code FileChannel} it does not define methods for mapping a
 * region of the file directly into memory. Where memory mapped I/O is required,
 * then a {@code FileChannel} can be used.
 *
 * <p> Asynchronous channels are bound to an asynchronous channel group for the
 * purpose of resource sharing. A group has an associated {@link
 * java.util.concurrent.ExecutorService} to which tasks are submitted to handle
 * I/O events and dispatch to completion handlers that consume the result of
 * asynchronous operations performed on channels in the group. The group can
 * optionally be specified when creating the channel or the channel can be bound
 * to a <em>default group</em>. Sophisticated users may wish to create their
 * own asynchronous channel groups or configure the {@code ExecutorService}
 * that will be used for the default group.
 *
 * <p> As with selectors, the implementation of asynchronous channels can be
 * replaced by "plugging in" an alternative definition or instance of the {@link
 * java.nio.channels.spi.AsynchronousChannelProvider} class defined in the
 * {@link java.nio.channels.spi} package.  It is not expected that many
 * developers will actually make use of this facility; it is provided primarily
 * so that sophisticated users can take advantage of operating-system-specific
 * asynchronous I/O mechanisms when very high performance is required.
 *
 * <p> Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in any class or interface in this package will cause a {@link
 * java.lang.NullPointerException NullPointerException} to be thrown.
 *
 * <p>There are two kinds of {@link SocketChannel} and {@link ServerSocketChannel}.
 *
 * <p><i>Internet protocol</i> channels support network
 * communication using TCP/IP and are addressed using {@link InetSocketAddress}es
 * which encapsulate an IP address and port number. <i>Internet protocol</i> channels
 * are the default kind created, when a protocol family is not specified
 * in the factory creation method.
 *
 * <p><a id="unixdomain"></a> <i>Unix domain</i> channels
 * support local inter-process communication on the same host, and are addressed
 * using {@link UnixDomainSocketAddress}es which encapsulate a filesystem pathname
 * on the local system.  <i>Unix domain</i> channels
 * can only be created using {@link SocketChannel#open(ProtocolFamily)} or
 * {@link ServerSocketChannel#open(ProtocolFamily)} with the parameter value
 * {@link StandardProtocolFamily#UNIX}. Unix domain channels might not be supported on all platforms.
 * An attempt to create a Unix domain channel may throw {@link UnsupportedOperationException}.
 *
 * {@link UnixDomainSocketAddress}es contain a path which, when the address is bound to a channel,
 * has an associated socket file in the file-system with the same name as the path. Address instances
 * are created with either a {@link String} path name or a {@link Path}. Paths
 * can be either absolute or relative with respect to the current working directory.
 * <p>
 * If a Unix domain {@link SocketChannel} is automatically bound by connecting it
 * without calling {@link SocketChannel#bind(SocketAddress) bind} first, then its address
 * is <i>unnamed</i>; it has an empty path field, and therefore has no associated file
 * in the file-system. Explicitly binding a {@code SocketChannel} to any unnamed
 * address has the same effect.
 * <p>
 * If a Unix domain {@link ServerSocketChannel} is automatically bound by passing a {@code null}
 * address to one of the {@link ServerSocketChannel#bind(SocketAddress) bind} methods, the channel
 * is bound to a unique name in the temporary directory identified by the {@code "java.io.tmpdir"}
 * system property. The exact pathname can be obtained by calling
 * {@link ServerSocketChannel#getLocalAddress() getLocalAddress} after bind returns.
 * It is an error to bind a {@code ServerSocketChannel} to an unnamed address.
 *
 * <p> A Unix domain channel can be bound to a name if and only if, no file exists
 * in the file-system with the same name, and the calling process has the required
 * operating system permissions to create a file of that name.
 * The socket file that is created when a channel binds to a name is not removed when
 * the channel is closed. User code must arrange for the deletion of this file if
 * another channel needs to bind to the same name. Note, this also applies to automatically
 * bound {@code ServerSocketChannel}s. Note also, that it may be possible
 * to delete the socket file, even before the channel is closed, thus allowing another
 * channel to bind to the same name. The original channel is not notified of any error
 * in this situation. Operating system permissions can be used to control who is allowed
 * to create and delete these socket files. Note also, that each platform enforces an
 * implementation specific, maximum length for the name of a Unix domain channel.
 * This limitation is enforced when a channel is bound. The maximum length is typically
 * close to and generally not less than 100 bytes.
 *
 * <p> If a security manager is present then using a <i>Unix Domain</i> SocketChannel
 * or ServerSocketChannel requires the {@link NetPermission NetPermission}
 * {@code ("allowUnixDomainChannels")}.
 *
 * @since 1.4
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 */

package java.nio.channels;

import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.UnixDomainSocketAddress;
import java.nio.file.Path;
